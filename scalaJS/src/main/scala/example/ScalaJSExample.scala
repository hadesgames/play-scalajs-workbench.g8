package example

import scala.scalajs.js
import js.Dynamic.{ global => g }
import js.annotation.JSExport
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExport

@JSExport
object ScalaJSExample {
  @JSExport
  def main(): Unit = {
    g.document.getElementById("scalajsShoutOut").textContent = "Hello world"
  }

  /** Computes the square of an integer.
    *  This demonstrates unit testing.
    */
  def square(x: Int): Int = x*x
}
