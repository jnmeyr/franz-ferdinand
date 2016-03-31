name := """franz-ferdinand"""

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  filters,

  "com.ibm"                %% "couchdb-scala"            % "0.7.0",
  "org.scalaz"             %% "scalaz-core"              % "7.1.3",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",

   "org.scalatest"          % "scalatest_2.11"           % "2.2.6" % Test
)

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

fork in run := false
