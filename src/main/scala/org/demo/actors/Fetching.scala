package org.demo.actors

import akka.actor._
import cats.effect.{ExitCode, IO}
import org.demo.actors.Fetching.Run
import cats.implicits._

import scala.io.StdIn

final class Fetching extends Actor{
  private implicit val catsContext = IO.contextShift(context.dispatcher)
  private implicit val catTimer = IO.timer(context.dispatcher)
  //private val redis =
  override def preStart(): Unit = ???
  override def postStop(): Unit = ???
  override def receive: Receive = {
    case Run =>
      Fetch.run[IO](fetchUser)
  }
}

object Fetching {

  case object Run

  def props: Props = ???
  def path :String = ""
}