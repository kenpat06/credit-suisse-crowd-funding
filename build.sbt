// Build file for Peter Pilgrim for Credit Suisse crowding application

// Define the project name
name := "credit-suisse-crowd-funding"

// Define the version
version := "1.0"

// What version of Scala do we need?
scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

/* 
  Weird snafu with the std libs - commented out direct dependencies
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
 */
//libraryDependencies +=
libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.37.0",
  "com.twitter" %% "finatra-http" % "2.3.0",
  "junit" % "junit" % "4.12" % "test",
  "org.mockito" % "mockito-all" % "1.10.8" % "test",
  "org.scalactic" %% "scalactic" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)


// mainClass in (Compile,run) := Some("uk.co.xenonique.agileit.XYZ")

// fork a new JVM for 'run' and 'test:run'
fork := true

// fork a new JVM for 'test:run', but not 'run'
fork in Test := true

// add a JVM option to use when forking a JVM for 'run'
javaOptions += "-Xmx512M"

