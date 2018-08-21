package com.mdsol.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.mdsol.api.routes.{BudgetRoutes, HealthCheckRoutes, SwaggerRoutes}
import com.mdsol.server.budget.BudgetResource
import com.mdsol.server.healthcheck.HealthcheckResource

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object QuickstartServer extends App {

  implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val routes = SwaggerRoutes.routes ~
    HealthcheckResource.routes(new HealthCheckRoutes) ~
    BudgetResource.routes(new BudgetRoutes)

  Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/")

  Await.result(system.whenTerminated, Duration.Inf)
}
