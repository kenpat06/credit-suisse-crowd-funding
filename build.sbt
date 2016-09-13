// Build file for Peter Pilgrim for Credit Suisse crowding application

// Define the project name
name := "credit-suisse-crowd-funding"

// Define the version
version := "1.0"

// What version of Scala do we need?
scalaVersion := "2.11.8"

// Force JDK 8
scalacOptions += "-target:jvm-1.8"

lazy val versions = new {
  val finagle = "6.37.0"
  val finatra = "2.4.0"
  val guice = "4.0"
  val junit = "4.12"
  val logback = "1.1.7"
  val mockito = "1.10.8"
  val scalatest = "3.0.0"
  val slf4j = "1.7.21"
  val specs2 = "2.3.12"
  val swagger = "0.5.0"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

/* 
  Weird snafu with the std libs - commented out direct dependencies
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
 */
libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % versions.slf4j,
  "ch.qos.logback" % "logback-classic" % versions.logback,
  "com.twitter" %% "finagle-http" % versions.finagle,
  "com.twitter" %% "finatra-http" % versions.finatra,
  "junit" % "junit" % versions.junit % "test",
  "org.mockito" % "mockito-all" % versions.mockito % "test",
  "org.scalactic" %% "scalactic" % versions.scalatest,
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",

  // Finatra need to rethink this test dependency nightmare! What the blazes?
  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "ch.qos.logback" % "logback-classic" % versions.logback % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests"
)

// set the main class for 'sbt run'
mainClass in (Compile,run) := Some("uk.co.xenonique.clients.cs.crowdfund.loan.CrowdSourceServerApp")

// fork a new JVM for 'run' and 'test:run'
fork := true

// fork a new JVM for 'test:run', but not 'run'
fork in Test := true

// add a JVM option to use when forking a JVM for 'run'
javaOptions += "-Xmx512M"

