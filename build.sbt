import Dependencies._
import sbt.Keys._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.mdsol",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "siteBudgets",
    libraryDependencies ++= Seq(
      scalaTest,
      mockito
    ).map(_ % Test),
    libraryDependencies ++= circe ++ Seq(
      akkahttp,
      akkaStream,
      catsCore,
      scanamo
    )
  )

lazy val openApiFile = "src/main/resources/swagger.yaml"
guardrailTasks in Compile := List(
  Server(file(openApiFile), pkg="com.mdsol.server")
)

guardrailTasks in Test := List(
  Client(file(openApiFile), pkg="com.mdsol.client")
)