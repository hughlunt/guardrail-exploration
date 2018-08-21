import sbt._

object Dependencies {

  lazy val akkahttp = "com.typesafe.akka" %% "akka-http" % "10.1.1"
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.14"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val catsCore = "org.typelevel" %% "cats-core" % "1.0.1"
  lazy val scanamo = "com.gu" %% "scanamo" % "1.0.0-M6"
  lazy val mockito = "org.mockito" % "mockito-core" % "2.19.0"

  val circeVersion =  "0.9.3"
  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-java8"
  ).map(_ % circeVersion)
}
