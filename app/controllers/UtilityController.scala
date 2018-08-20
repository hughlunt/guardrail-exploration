package controllers

import play.api.libs.json.Json
import play.api.mvc._
import buildInfo.BuildInfo

case class UtilityController(controllerComponents: ControllerComponents)
    extends BaseController {

  def applicationStatus() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.parse("""{"status":"OK"}"""))
  }

  def applicationVersion() = Action { implicit request: Request[AnyContent] =>
    Ok(BuildInfo.toJson).as(JSON)
  }
}
