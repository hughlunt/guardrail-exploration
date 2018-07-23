package controllers

import play.api.mvc._

case class UtilityController(controllerComponents: ControllerComponents)
    extends BaseController {

  def applicationStatus() = Action { implicit request: Request[AnyContent] =>
    Ok("Application is running")
  }

  def applicationVersion() = Action { implicit request: Request[AnyContent] =>
    Ok("Version XYZ")
  }
}
