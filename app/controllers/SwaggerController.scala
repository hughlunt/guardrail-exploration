package controllers

import play.api.mvc.{BaseController, ControllerComponents}

case class SwaggerController(controllerComponents: ControllerComponents)
  extends BaseController {

  def redirectDocs() = Action {
    Redirect(
      url = "/assets/lib/swagger-ui/index.html",
      queryString = Map("url" -> Seq("/assets/swagger.json"))
    )
  }
}
