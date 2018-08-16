package controllers

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class HealthCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getHealth = Action { implicit request: Request[AnyContent] =>
    Ok(Json.parse("""{"status":"OK"}"""))
  }
}
