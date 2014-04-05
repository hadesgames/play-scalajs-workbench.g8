import sbt._
import Keys._

object ApplicationBuild extends Build {

  override def rootProject = Some(scalaJVM)

  lazy val scalaJVM = Project(
    id = "scalaJVM",
    base = file("scalajvm")
  ).settings(scalaJVMSettings: _*)
   .dependsOn(scalaShared)

  lazy val scalaJS = Project(
    id = "scalaJS",
    base = file("scalaJS")
  ).settings (scalaJSSettings: _*)
   .dependsOn (scalaShared)

  lazy val scalaShared = Project(
    id = "scalaShared",
    base = file("shared")
  ).settings(scalaSharedSettings: _*)

  lazy val scalaJVMSettings = Seq(
    libraryDependencies ++= Seq(

    )
  )

  lazy val scalaJSSettings = Seq(
    libraryDependencies ++= Seq(

    )
  )

  lazy val scalaSharedSettings = Seq(
    libraryDependencies ++= Seq(

    )
  )

}