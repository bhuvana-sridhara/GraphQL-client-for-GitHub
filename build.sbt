name := "github-graphql-scala"

version := "0.1"

libraryDependencies := Seq("com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11")

scalaVersion := "2.13.2"

// https://mvnrepository.com/artifact/org.scalaj/scalaj-http
libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
  //"com.fasterxml.jackson.core" % "jackson-databind" % "2.10.3",
  //"io.spray" %%  "spray-json" % "1.3.5"
  // https://mvnrepository.com/artifact/com.google.code.gson/gson
  "com.google.code.gson" % "gson" % "2.8.6",
  // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.0",
  // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.0",
  // https://mvnrepository.com/artifact/org.scala-lang/scala-reflect
  "org.scala-lang" % "scala-reflect" % "2.13.2",
   // https://mvnrepository.com/artifact/org.scalatest/scalatest
  "org.scalatest" %% "scalatest" % "3.1.1" % "test"
)