import sbt._
import Keys._
import play.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys
import com.lihaoyi.workbench.Plugin._

object ApplicationBuild extends Build with UniversalKeys {

  override def rootProject = Some(scalaJVM)

  val scalaJsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  lazy val scalaJVM = play.Project(
    name = "scalaJVM",
    path = file("scalajvm")
  ).settings(scalaJVMSettings: _*)
   .dependsOn(scalaJS)
   .dependsOn(scalaShared)

  lazy val scalaJS = Project(
    id = "scalaJS",
    base = file("scalaJS")
  ).settings (scalajsSettings: _*)
   .dependsOn (scalaShared)

  lazy val scalaShared = Project(
    id = "scalaShared",
    base = file("shared")
  ).settings(scalaSharedSettings: _*)

  lazy val scalaJVMSettings = play.Project.playScalaSettings ++ Seq(
    // Setup the scala-js project
    scalaJsOutputDir :=  (crossTarget in Compile).value / "classes" / "public" / "javascripts",
    compile in Compile <<= (compile in Compile) dependsOn (packageJS in (scalaJS, Compile)),
    dist <<= dist dependsOn (optimizeJS in (scalaJS, Compile)),
    crossTarget in (scalaJS, Compile) := scalaJsOutputDir.value,

    libraryDependencies ++= Seq(
        jdbc,
        anorm,
        cache
    )
  )

  lazy val scalajsSettings = scalaJSSettings ++
      Seq(
         libraryDependencies ++= Seq(

        )
      ) ++ workbenchSettings ++
      Seq(
        // Workbench configurations
        bootSnippet := "ScalaJS.modules.example_ScalaJSExample().main();",
        // Rewrite the urls for the scala workbench
        updatedJS <<= updatedJS map { paths =>
          val pattern = "classes/public/"
          paths map { path =>
            if (path.contains(pattern))
              "http://localhost:9000/assets/" + path.substring(path.indexOf(pattern) + pattern.length)
            else
              path
          }
        },
        updateBrowsers <<= updateBrowsers.triggeredBy(ScalaJSKeys.packageJS in Compile)
      )

  lazy val scalaSharedSettings = scalaJSSettings ++ Seq(
    libraryDependencies ++= Seq(

    )
  )

}