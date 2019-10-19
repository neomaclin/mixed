package org.demo.core

import cats.effect._
import cats.implicits._
import scala.io.StdIn


object Main extends IOApp {

//  def main(args: Array[String]): Unit = {
//    println("请输入姓名：") ; val name:String = StdIn.readLine() ; println("您的姓名是："+name) ; sys.exit(0)
//  }

  override def run(args: List[String]): IO[ExitCode] = {
    (
      (IO(println("请输入姓名：")) *> IO(StdIn.readLine()) ).>>=(name => IO(println("您的姓名是：" + name)))
    ) as ExitCode.Success
  }

}