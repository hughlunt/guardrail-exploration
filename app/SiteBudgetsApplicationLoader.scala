import controllers.{HomeController, SwaggerController, UtilityController}
import play.api.mvc.EssentialFilter
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import play.api.ApplicationLoader.Context
import router.Routes

class SiteBudgetsApplicationLoader extends ApplicationLoader {

  override def load(context: ApplicationLoader.Context): Application =
    new SiteBudgetsComponents(context).application
}

class SiteBudgetsComponents(context: Context) extends BuiltInComponentsFromContext(context) {

//  lazy val homeController: HomeController = HomeController(controllerComponents)
  lazy val swaggerController: SwaggerController = SwaggerController(controllerComponents)
  lazy val utilityController: UtilityController = UtilityController(controllerComponents)

  lazy val router = new Routes(
    httpErrorHandler,
    swaggerController,
    utilityController
  )

  override def httpFilters: Seq[EssentialFilter] = Seq()
}
