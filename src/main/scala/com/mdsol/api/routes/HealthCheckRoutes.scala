package com.mdsol.api.routes

import com.mdsol.server.healthcheck.{HealthcheckHandler, HealthcheckResource}

import scala.concurrent.Future

class HealthCheckRoutes extends HealthcheckHandler {
  override def getHealth(respond: HealthcheckResource.getHealthResponse.type)(): Future[HealthcheckResource.getHealthResponse] =
    Future.successful(HealthcheckResource.getHealthResponseOK)
}
