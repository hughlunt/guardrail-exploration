import sbt._

object Dependencies {

  lazy val akkahttp = "com.typesafe.akka" %% "akka-http" % "10.1.1"

  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.14"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

  val circeVersion =  "0.9.3"
  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-java8"
  ).map(_ % circeVersion)
}
