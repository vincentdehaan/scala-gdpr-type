name := "scala-gdpr-type"

version := "0.1-SNAPSHOT"


scalaVersion := "2.13.5"

lazy val core = (project in file("core"))
  .dependsOn(coreMacros)
  .settings(
    organization := "nl.vindh",
    name := "scala-gdpr-type-core",
    scalaVersion := "2.13.5",
    crossScalaVersions := Seq("2.12.12", "2.13.5"),
    libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.3",
      "org.typelevel" %% "cats-core" % "2.6.1"
    ),
    scalacOptions += "-language:experimental.macros"
  )

lazy val coreMacros = (project in file("core-macros"))
  .settings(
    organization := "nl.vindh",
    name := "scala-gdpr-type-macros",
    scalaVersion := "2.13.5",
    crossScalaVersions := Seq("2.12.12", "2.13.5"),
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    scalacOptions += "-language:experimental.macros"
  )

lazy val example = (project in file("example"))
  .dependsOn(core)
  .settings(
    scalaVersion := "2.13.5",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1"
  )