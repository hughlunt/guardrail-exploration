package controllers

import org.scalatestplus.play.PlaySpec
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._

class UtilityControllerSpec extends PlaySpec with Results  {
  "UtilityController GET /app_status" should {
    "return a status OK from a new instance of controller" in {
      val controller = UtilityController(stubControllerComponents())
      val statusResult = controller.applicationStatus().apply(FakeRequest(GET, "app_status"))

      status(statusResult) mustBe OK
      contentType(statusResult) mustBe Some("application/json")
      contentAsString(statusResult) mustBe """{"status":"OK"}"""
    }
  }
}
