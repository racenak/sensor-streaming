name := "streaming"
version := "1.0-SNAPSHOT"
organization := "flink"

scalaVersion := "2.12.18"

val flinkVersion = "1.20.3"
val scalaBinaryVersion = "2.12"

lazy val root = (project in file("."))
  .settings(
    name := "streaming",
    scalaVersion := "2.12.18",
    
    libraryDependencies ++= Seq(
      // Flink Core
      "org.apache.flink" %% "flink-scala" % flinkVersion,

      // Flink Scala API
      "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
      
      // Flink Clients
      "org.apache.flink" % "flink-clients" % flinkVersion,

      // Flink Kafka Connector
      "org.apache.flink" % "flink-connector-kafka" % "3.4.0-1.20",
      
      // Scala Library
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      
      // Test Dependencies
      "org.scalatest" %% "scalatest" % "3.2.15" % Test
    ),
    
    // Java version
    javacOptions ++= Seq("-source", "17", "-target", "17"),
    
    // Assembly plugin settings for creating fat JAR
    assembly / mainClass := Some("flink.FlinkKafkaJob"),
    assembly / assemblyJarName := s"${name.value}-${version.value}.jar",
    
    // Exclude files from assembly
    assembly / assemblyExcludedJars := {
      val cp = (assembly / fullClasspath).value
      cp.filter { file =>
        file.data.getName.contains("flink-shaded-force-shading") ||
        file.data.getName.contains("jsr305") ||
        file.data.getName.contains("slf4j") ||
        file.data.getName.contains("log4j")
      }
    },
    
    // Merge strategy for assembly
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => 
        xs.map(_.toLowerCase) match {
          case "services" :: _ :: Nil => MergeStrategy.filterDistinctLines
          case "spring.schemas" :: Nil | "spring.handlers" :: Nil => MergeStrategy.filterDistinctLines
          case _ => MergeStrategy.discard
        }
      case _ => MergeStrategy.first
    }
  )
