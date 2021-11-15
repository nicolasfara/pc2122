val scala3Version = "3.1.0"

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / homepage := Some(url("https://github.com/nicolasfara/pc2122"))
ThisBuild / organization := "it.unibo.pc"
ThisBuild / licenses := List("Apache 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))

ThisBuild / scalaVersion := scala3Version

val scalaTest = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scalatest" %% "scalatest" % "3.2.10" % "test"
)

val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.15.4" % "test"
val scalaChart = "de.sciss" %% "scala-chart" % "0.8.0"

lazy val root = project
  .in(file("."))
  .aggregate(core, examples, lab)

lazy val core = project
  .in(file("core"))
  .settings(
    name := "pc2122-lab",
    libraryDependencies ++= scalaTest,
    libraryDependencies += scalaCheck
  )

lazy val examples = project
  .in(file("examples"))
  .dependsOn(core)

lazy val lab = project
  .in(file("lab"))
  .dependsOn(core)
