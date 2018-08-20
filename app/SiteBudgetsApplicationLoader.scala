import play.api.{Application, ApplicationLoader}

class SiteBudgetsApplicationLoader extends ApplicationLoader {

  override def load(context: ApplicationLoader.Context): Application =
    new SiteBudgetsComponents(context).application
}


