package com.mdsol.api.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives

object SwaggerRoutes extends Directives {
  val routes = pathPrefix("docs" / "swagger-ui") {
    getFromResourceDirectory("swagger-ui")
  } ~
  path("docs"/ "swagger" / "swagger.yaml") {
    getFromResource("swagger.yaml")
  } ~
  pathEndOrSingleSlash {
    redirect("/docs/swagger-ui/index.html?url=/docs/swagger/swagger.yaml", StatusCodes.PermanentRedirect)
  }
}
