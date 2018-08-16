import controllers.{SwaggerController, UtilityController}
import play.api.ApplicationLoader.Context
import play.api.BuiltInComponentsFromContext
import play.api.mvc.EssentialFilter
import router.Routes

class SiteBudgetsComponents(context: Context) extends BuiltInComponentsFromContext(context) {

  lazy val swaggerController: SwaggerController = SwaggerController(controllerComponents)
  lazy val utilityController: UtilityController = UtilityController(controllerComponents)

  lazy val router = new Routes(
    httpErrorHandler,
    utilityController,
    swaggerController
  )

  override def httpFilters: Seq[EssentialFilter] = Seq()
}
