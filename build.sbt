lazy val myProject =
  (project in file("."))
    .enablePlugins(JavaAppPackaging, SbtTwirl)

name := "wncore"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8", "-language:postfixOps")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-contrib" % "2.4.8",
  "net.virtual-void" %% "json-lenses" % "0.6.1",
  "com.google.inject" % "guice" % "4.1.0",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "com.ibm.watson.developer_cloud" % "java-sdk" % "3.5.1",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
  "commons-codec" % "commons-codec" % "1.3",
  "org.apache.httpcomponents" % "httpclient" % "4.0.1",
  "org.apache.httpcomponents" % "httpcore" % "4.0.1",
  "commons-logging" % "commons-logging" % "1.2",
  "wit" % "duckling" % "0.4.13",
  "com.github.mfornos" % "humanize-slim" % "1.2.2",
  "joda-time" % "joda-time" % "2.9.6",
  "org.joda" % "joda-convert" % "1.8",
  "com.googlecode.libphonenumber" % "libphonenumber" % "7.7.2",
  "net.oauth.core" % "oauth" % "20090617",
  "net.oauth.core" % "oauth-httpclient4" % "20090913",
  "org.scribe" % "scribe" % "1.3.7",
  "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0",
  "com.github.etaty" %% "rediscala" % "1.7.0",
  "com.vdurmont" % "emoji-java" % "3.1.3",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "btomala" %% "akka-http-twirl" % "1.1.0" excludeAll
    ExclusionRule(organization = "com.typesafe.akka")
)

unmanagedBase <<= baseDirectory { base => base / "libs" }

Revolver.settings

resolvers += "Bartek's repo at Bintray" at "https://dl.bintray.com/btomala/maven"

resolvers += "clojars.org" at "http://clojars.org/repo"
