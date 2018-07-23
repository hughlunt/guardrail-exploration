import controllers.HomeController
import play.api.mvc.EssentialFilter
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import router.Routes

class SiteBudgetsApplicationLoader extends ApplicationLoader {

  override def load(context: ApplicationLoader.Context): Application = new SiteBudgetsComponents(context).application

}

import play.api.ApplicationLoader.Context

class SiteBudgetsComponents(context: Context) extends BuiltInComponentsFromContext(context) {

  lazy val homeController: HomeController = HomeController(controllerComponents)

//  override def router = new Routes(httpErrorHandler, homeController)
  lazy val router = new Routes(httpErrorHandler, homeController)

  override def httpFilters: Seq[EssentialFilter] = Seq()
}

/*
import play.api._
import play.api.ApplicationLoader.Context
import play.filters.HttpFiltersComponents
import router.Routes

class MyApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    new MyComponents(context).application
  }
}

class MyComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents
    with controllers.AssetsComponents {
  lazy val barRoutes = new bar.Routes(httpErrorHandler)
  lazy val applicationController = new controllers.Application(controllerComponents)

  lazy val router = new Routes(httpErrorHandler, applicationController, barRoutes, assets)
}
 */
