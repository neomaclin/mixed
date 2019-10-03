package org.demo.core

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.demo.env.{SparkEnv, SparkZIO}
import zio.{Managed, RIO, Task, UIO, ZIO, App => ZApp}
import org.apache.spark.sql.Encoders
import org.demo.env.SparkEnv.TaskS

object BigDataSpark extends ZApp {

  val sparkEnv = new SparkZIO(
    SparkSession.builder.master("local[*]").getOrCreate()
  )

  val prg: RIO[SparkEnv, Unit] =
    for {
      env <- ZIO.environment[SparkEnv]
      textFile <- env.read.textFile("hdfs://...")
     // counts <- textFile.flatMap(_.split(" ")).map(_ -> 1).reduceByKey(_ + _)
   //   _ <- env.write(counts).text("hdfs://...")
      _ <- env.ss.map(_.stop())
    } yield ()

  override def run(args: List[String]): ZIO[Environment, Nothing, Int] =
    prg.provide(sparkEnv).fold(_ => 0, _ => 1)

}
