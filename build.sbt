name := """patients-base"""
organization := "com.renan"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.3"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.5.0"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.3.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.3.0"
dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.renan.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.renan.binders._"
