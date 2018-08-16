package controllers

import play.api.libs.json.Json
import play.api.mvc._

case class HealthCheckController(controllerComponents: ControllerComponents)
    extends BaseController {

  def getHealth = Action { implicit request: Request[AnyContent] =>
    Ok(Json.parse("""{"status":"OK"}"""))
  }
}
