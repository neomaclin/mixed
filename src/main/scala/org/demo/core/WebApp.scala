package org.demo.core

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import cats.effect.{IO, _}
import cats.implicits._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import monix.eval.{Task, TaskApp}

import scala.io.StdIn

object WebApp extends TaskApp {

  val environment: Resource[Task, (ActorSystem, ActorMaterializer)] = for {
    sys <- Resource.make(Task(ActorSystem("akka-system")))(s => Task(s.terminate()))
    mat <- Resource.make(Task(ActorMaterializer()(sys)))(m => Task(m.shutdown()))
  } yield (sys, mat)

  val route: Route = path("hello") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    }
  }

  def executionLogic(routes: Route)(resource: (ActorSystem, ActorMaterializer)): Task[_] = {
    implicit val (system, mat) = resource
    for {
      h <- Task.defer(Task.fromFuture(Http().bindAndHandle(routes, "localhost", 8080)))
      _ <- Task(println(s"Checkout http://localhost:8080/hello\nPress RETURN to stop...")) *> Task(StdIn.readLine())
      done <- Task(h.unbind())
    } yield done
  }

  override def run(args: List[String]): Task[ExitCode] = environment.use(executionLogic(route)).as(ExitCode.Success)

}