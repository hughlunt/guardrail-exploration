import Dependencies._
import sbt.Keys._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "guardRailTest",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= circe ++ Seq(
      akkahttp,
      akkaStream
    )
  )

guardrailTasks in Compile := List(
  Server(file("src/main/resources/swagger.json"), pkg="com.mdsol.server")
)