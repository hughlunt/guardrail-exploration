package controllers

import org.scalatestplus.play._
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._

class HealthCheckControllerSpec extends PlaySpec with Results {

  "HomeController GET" should {
    "return OK" in {
      val controller = HealthCheckController(stubControllerComponents())
      val home = controller.getHealth.apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include ("""{"status":"OK"}""")
    }
  }
}
