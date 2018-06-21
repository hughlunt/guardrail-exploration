name := """site_budgets"""
organization := "com.mdsol"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.webjars" % "swagger-ui" % "2.2.0"
libraryDependencies += "com.gu" %% "scanamo" % "1.0.0-M6"
libraryDependencies += "org.mockito" % "mockito-core" % "2.19.0" % Test

swaggerDomainNameSpaces := Seq("domain.entities")
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.mdsol.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.mdsol.binders._"
