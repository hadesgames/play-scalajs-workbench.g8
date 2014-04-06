import sbt._
import Keys._
import play.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys

object ApplicationBuild extends Build with UniversalKeys {

  override def rootProject = Some(scalaJVM)
  val scalaJsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  lazy val scalaJVM = play.Project(
    name = "scalaJVM",
    path = file("scalajvm")
  ).settings(scalaJVMSettings: _*)
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
    scalaJsOutputDir :=  (crossTarget in Compile).value / "classes" / "public" / "javascripts",
    compile in Compile <<= (compile in Compile) dependsOn (packageJS in (scalaJS, Compile)),
    dist <<= dist dependsOn (optimizeJS in (scalaJS, Compile)),


    libraryDependencies ++= Seq(
        jdbc,
        anorm,
        cache
    )
  ) ++ (
    // ask scalajs project to put its outputs in scalajsOutputDir
    Seq(packageExternalDepsJS, packageInternalDepsJS, packageExportedProductsJS, packageJS, preoptimizeJS, optimizeJS) map {
      packageJSKey =>
        crossTarget in (scalaJS, Compile, packageJSKey) := scalaJsOutputDir.value
    }
  )

  lazy val scalajsSettings = scalaJSSettings ++ Seq(
    libraryDependencies ++= Seq(

    )
  )

  lazy val scalaSharedSettings = Seq(
    libraryDependencies ++= Seq(

    )
  )

}