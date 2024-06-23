val scala3Version = "3.0.1"

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / homepage := Some(url("https://github.com/nicolasfara/pc2122"))
ThisBuild / organization := "it.unibo.pc"
ThisBuild / licenses := List("Apache 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))

ThisBuild / scalaVersion := scala3Version

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

val scalaTest = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
)

val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.15.4" % "test"
val scalaChart = "de.sciss" %% "scala-chart" % "0.8.0"
val scalaContrib = ("org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.2").cross(CrossVersion.for3Use2_13)

lazy val root = project
  .in(file("."))
  .aggregate(core, examples, lab)

lazy val core = project
  .in(file("core"))
  .settings(
    name := "pc2122-lab",
    libraryDependencies ++= scalaTest,
    libraryDependencies ++= Seq(scalaCheck, scalaChart, scalaContrib),
  )

lazy val examples = project
  .in(file("examples"))
  .dependsOn(core)

lazy val lab = project
  .in(file("lab"))
  .dependsOn(core)
  .settings(
    libraryDependencies ++= scalaTest,
    libraryDependencies += scalaCheck,
  )
