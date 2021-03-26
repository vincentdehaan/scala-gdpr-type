name := "scala-gdpr-type"

version := "0.1"

scalaVersion := "2.13.5"

lazy val core = (project in file("core"))
  .dependsOn(coreMacros)
  .settings(
    scalaVersion := "2.13.5",
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3",
    scalacOptions += "-language:experimental.macros"
  )

lazy val coreMacros = (project in file("core-macros"))
  .settings(
    scalaVersion := "2.13.5",
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    scalacOptions += "-language:experimental.macros"
  )

lazy val example = (project in file("example"))
  .dependsOn(core)
  .settings(
    scalaVersion := "2.13.5"
  )