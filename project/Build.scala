import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys
import com.lihaoyi.workbench.Plugin._

object ApplicationBuild extends Build with UniversalKeys {

  override def rootProject = Some(scalaJvm)

  val scalaJsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  lazy val scalaJvm = play.Project(
    name = "scala-jvm",
    path = file("scala-jvm")
  ).settings(jvmSettings: _*)
   .dependsOn(scalaJS)
   .dependsOn(scalaShared)

  lazy val scalaJS = Project(
    id = "scala-js",
    base = file("scala-js")
  ).settings (jsSettings: _*)
   .dependsOn (scalaShared)

  lazy val scalaShared = Project(
    id = "scala-shared",
    base = file("scala-shared")
  ).settings(sharedSettings: _*)


  lazy val jvmSettings = baseJvmSettings ++ Seq(
    /* Add extra configurations for the Jvm Project here */
  )

  lazy val jsSettings = baseJsSettings ++ Seq(
    /* Add extra configurations for the Js Project here */
  )

  lazy val sharedSettings = baseSharedSettings ++ Seq(
    /* Add extra configuration for the Shared Project here */
  )

  /* Base configuration for each project */
  lazy val baseJvmSettings = play.Project.playScalaSettings ++ Seq(
    // Setup the scala-js project
    scalaJsOutputDir :=  (crossTarget in Compile).value / "classes" / "public" / "javascripts",
    compile in Compile <<= (compile in Compile) dependsOn (packageJS in (scalaJS, Compile)),
    dist <<= dist dependsOn (optimizeJS in (scalaJS, Compile)),
    crossTarget in (scalaJS, Compile) := scalaJsOutputDir.value
  )

  lazy val baseJsSettings = ScalaJSPlugin.scalaJSSettings ++
      workbenchSettings ++
      Seq(
        // Workbench configurations
        bootSnippet := "ScalaJS.modules.example_ScalaJSExample().main();",
        // Rewrite the urls for the scala workbench
        updatedJS <<= updatedJS map { paths =>
          val pattern = "classes/public/"
          paths map { path =>
            println("Path:", path)
            if (path.contains(pattern))
              "http://localhost:9000/assets/" + path.substring(path.indexOf(pattern) + pattern.length)
            else
              path
          }
        },
        updateBrowsers <<= updateBrowsers.triggeredBy(packageJS in Compile)
      )

  lazy val baseSharedSettings = ScalaJSPlugin.scalaJSSettings
}