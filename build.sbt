name := "test"

version := "1.0"

scalaVersion := "2.12.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
libraryDependencies += "org.scalamock" % "scalamock-scalatest-support_2.12" % "3.5.0"
libraryDependencies += "org.mockito" % "mockito-core" % "2.7.22"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.0"

libraryDependencies += "ch.qos.logback"    %  "logback-classic" % "1.1.3"

libraryDependencies += "com.google.api-client" % "google-api-client" % "1.22.0"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.22.0"
libraryDependencies += "com.google.apis" % "google-api-services-drive" % "v3-rev83-1.22.0"