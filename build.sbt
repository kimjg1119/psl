val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "psl",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.14.3",
      "io.circe" %% "circe-generic" % "0.14.3",
      "io.circe" %% "circe-parser" % "0.14.3",
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      ("org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0")
        .cross(CrossVersion.for3Use2_13)
    )
  )
