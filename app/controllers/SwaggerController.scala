package controllers

import com.google.inject.Inject
import play.api.mvc._

class SwaggerController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def redirectDocs = Action {
    Redirect(url = "/assets/lib/swagger-ui/index.html", queryString = Map("url" -> Seq("/assets/swagger.json")))
  }

}
