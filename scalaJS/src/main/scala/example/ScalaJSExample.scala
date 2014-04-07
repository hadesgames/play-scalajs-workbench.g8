package example

import scala.scalajs.js
import js.Dynamic.{ global => g }
import js.annotation.JSExport
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExport
import shared.SharedMessages

@JSExport
object ScalaJSExample {
  @JSExport
  def main(): Unit = {
    g.document.getElementById("scala-demo").textContent = "Helloworld !" + SharedMessages.itWorks
  }

  /** Computes the square of an integer.
    *  This demonstrates unit testing.
    */
  def square(x: Int): Int = x*x
}
