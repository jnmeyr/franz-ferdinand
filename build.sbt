name := """franz-ferdinand"""

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  filters,
  "org.scalatest" % "scalatest_2.11" % "2.2.6" % Test,
  "org.scalaz" %% "scalaz-core" % "7.2.1",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
)

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

fork in run := false
