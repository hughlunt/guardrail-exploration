package controllers

import org.scalatestplus.play._
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

  "UtilityController GET /app_version" should {
    "return a build information json from a new instance of controller" in {
      val controller = UtilityController(stubControllerComponents())
      val statusResult = controller.applicationVersion().apply(FakeRequest(GET, "app_version"))

      status(statusResult) mustBe OK
      contentType(statusResult) mustBe Some("application/json")
      val resultBodyText: String = contentAsString(statusResult)

      resultBodyText.contains("builtAtMillis") mustBe true
      resultBodyText.contains("gitCommit") mustBe true
      resultBodyText.contains("scalaVersion") mustBe true
      resultBodyText.contains("version") mustBe true
      resultBodyText.contains("sbtVersion") mustBe true
      resultBodyText.contains("builtAtString") mustBe true
    }
  }
}
