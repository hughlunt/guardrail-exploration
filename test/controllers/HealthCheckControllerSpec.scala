package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class HealthCheckControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "return OK" in {
      val controller = new HealthCheckController(stubControllerComponents())
      val home = controller.getHealth.apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include ("""{"status":"OK"}""")
    }
  }
}
