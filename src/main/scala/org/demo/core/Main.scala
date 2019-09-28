package org.demo.core

import cats.effect._
import cats.implicits._
import monix.eval._

object Main extends TaskApp {

  override def run(args: List[String]): Task[ExitCode] = Task(println(Grooving.getOut + (new CallingClojure).apply())).as(ExitCode.Success)

}
