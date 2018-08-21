package com.mdsol.api.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import cats.data.EitherT
import com.mdsol.client.definitions.HealthCheckResult
import com.mdsol.client.healthcheck.HealthcheckClient
import com.mdsol.server.healthcheck.HealthcheckResource
import org.scalatest.{AsyncWordSpec, Matchers}

import scala.concurrent.Future

class HealthCheckRoutesSpec extends AsyncWordSpec with Matchers {

  "HealthCheckRoutes" when {

    implicit val system: ActorSystem = ActorSystem("testAkkaHttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    "getHealth" should {
      "return 'Everything is bon'" in {
        val healthCheckHttpClient: HttpRequest => Future[HttpResponse] =
          Route.asyncHandler(HealthcheckResource.routes(new HealthCheckRoutes))

        val healthCheckClient: HealthcheckClient = HealthcheckClient.httpClient(healthCheckHttpClient)

        val getHealthResponse: EitherT[Future, Either[Throwable, HttpResponse], HealthCheckResult] =
          healthCheckClient.getHealth()

        val expected = HealthCheckResult("Everything is bon")

        getHealthResponse.value.map(_ shouldBe Right(expected))
      }
    }
  }
}
