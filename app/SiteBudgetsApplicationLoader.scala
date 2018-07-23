import controllers.HomeController
import play.api.mvc.EssentialFilter
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import router.Routes

class SiteBudgetsApplicationLoader extends ApplicationLoader {

  override def load(context: ApplicationLoader.Context): Application =
    new SiteBudgetsComponents(context).application

}

import play.api.ApplicationLoader.Context

class SiteBudgetsComponents(context: Context)
    extends BuiltInComponentsFromContext(context) {

  lazy val homeController: HomeController = HomeController(controllerComponents)

  lazy val router = new Routes(httpErrorHandler, homeController)

  override def httpFilters: Seq[EssentialFilter] = Seq()
}
