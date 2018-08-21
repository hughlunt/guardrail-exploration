package com.mdsol.api.routes

import java.time.OffsetDateTime

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import cats.data.EitherT
import com.mdsol.client.Implicits
import com.mdsol.client.budget.BudgetClient
import com.mdsol.client.definitions.BudgetRequest
import com.mdsol.server.budget.BudgetResource
import org.scalatest.{AsyncWordSpec, Matchers}

import scala.concurrent.Future

class BudgetRoutesSpec extends AsyncWordSpec with Matchers {

  "BudgetRoutes" when {
    implicit val system: ActorSystem = ActorSystem("testAkkaHttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    "createBudget" should {
      "Return a successful response" in {
        val budgetHttpClient: HttpRequest => Future[HttpResponse] =
          Route.asyncHandler(BudgetResource.routes(new BudgetRoutes))

        val budgetClient: BudgetClient = BudgetClient.httpClient(budgetHttpClient)

        val request = BudgetRequest(
          "study",
          "site",
          "cta",
          OffsetDateTime.now(),
          OffsetDateTime.now(),
          Nil.toIndexedSeq
        )
        val getBudgetResponse: EitherT[Future, Either[Throwable, HttpResponse], Implicits.IgnoredEntity] = budgetClient.createBudget(request)

        getBudgetResponse.value.map(either => either should be ('right))
      }
    }
  }
}
