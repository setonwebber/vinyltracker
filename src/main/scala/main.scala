//> using scala 3.6.3
import scala.io.StdIn.readLine
import vinyls.*
import genres.*
import artists.*

@main
def main(): Unit =
  println("Please enter your name:")
  val name = readLine()

  println("Hello, " + name + "!")
    var v = new Vinyl
