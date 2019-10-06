package org.demo.datasources

import cats._
import cats.effect._

import cats.instances.list._
import cats.syntax.all._
import java.io._
import java.nio.charset.Charset
import fetch._
import redis.clients.jedis._

object Redis {

  def cacheOn[F[_] : Sync](host: String): DataCache[F] = new RedisCache(host)

  type ByteArray = Array[Byte]

  private def byteOutputStream[F[_]](implicit S: Sync[F]): Resource[F, ByteArrayOutputStream] =
    Resource.fromAutoCloseable(S.delay(new ByteArrayOutputStream()))

  private def byteInputStream[F[_]](bin: ByteArray)(
    implicit S: Sync[F]): Resource[F, ByteArrayInputStream] =
    Resource.fromAutoCloseable(S.delay(new ByteArrayInputStream(bin)))

  private def outputStream[F[_]](b: ByteArrayOutputStream)(
    implicit S: Sync[F]): Resource[F, ObjectOutputStream] =
    Resource.fromAutoCloseable(S.delay(new ObjectOutputStream(b)))

  private  def inputStream[F[_]](b: ByteArrayInputStream)(
    implicit S: Sync[F]): Resource[F, ObjectInputStream] =
    Resource.fromAutoCloseable(S.delay(new ObjectInputStream(b)))

  private def fromString(s: String): Array[Byte] =
    s.getBytes(Charset.forName("UTF-8"))

  private def serialize[F[_], A](obj: A)(
    implicit S: Sync[F]
  ): F[ByteArray] = {
    byteOutputStream
      .mproduct(outputStream(_))
      .use({
        case (byte, out) =>
          S.delay {
            out.writeObject(obj)
            out.flush()
            byte.toByteArray
          }
      })
  }

  private def deserialize[F[_], A](bin: ByteArray)(
    implicit S: Sync[F]
  ): F[Option[A]] = {
    byteInputStream(bin)
      .mproduct(inputStream(_))
      .use({
        case (_, in) =>
          S.delay {
            val obj = in.readObject()
            try Some(obj.asInstanceOf[A]) catch{
              case _: Throwable => None
            }
          }
      })
  }

  final class RedisCache[F[_] : Sync](host: String) extends DataCache[F] {
    private val pool = new JedisPool(host)

    def connection: Resource[F, Jedis] =
      Resource.fromAutoCloseable(Sync[F].delay(pool.getResource))

    private def get(i: Array[Byte]): F[Option[Array[Byte]]] =
      connection.use(c => Sync[F].delay(Option(c.get(i))))

    private def set(i: Array[Byte], v: Array[Byte]): F[Unit] =
      connection.use(c => Sync[F].delay(c.set(i, v)).void)

    private def bulkSet(ivs: List[(Array[Byte], Array[Byte])]): F[Unit] =
      connection.use(c =>
        Sync[F].delay({
          val pipe = c.pipelined
          ivs.foreach(i => pipe.set(i._1, i._2))
          pipe.sync
        }))

    private def cacheId[I, A](i: I, data: Data[I, A]): Array[Byte] =
      fromString(s"${data.identity} ${i}")

    override def lookup[I, A](i: I, data: Data[I, A]): F[Option[A]] =
      get(cacheId(i, data)) >>= {
        case None => Sync[F].pure(None)
        case Some(r) => deserialize[F, A](r)
      }

    override def insert[I, A](i: I, v: A, data: Data[I, A]): F[DataCache[F]] =
      for {
        s <- serialize(v)
        _ <- set(cacheId(i, data), s)
      } yield this

    override def bulkInsert[I, A](vs: List[(I, A)], data: Data[I, A])(
      implicit M: Monad[F]
    ): F[DataCache[F]] =
      for {
        bin <- vs.traverse({
          case (id, v) =>
            serialize(v).tupleRight(cacheId(id, data))
        })
        _ <- Sync[F].delay(bulkSet(bin))
      } yield this

  }


}
