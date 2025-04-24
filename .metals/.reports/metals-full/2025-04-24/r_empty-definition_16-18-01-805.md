error id: loadVinyls.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/main.scala
empty definition using pc, found symbol in pc: loadVinyls.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -vinyls/genres/loadVinyls.
	 -vinyls/genres/loadVinyls#
	 -vinyls/genres/loadVinyls().
	 -genres/genres/loadVinyls.
	 -genres/genres/loadVinyls#
	 -genres/genres/loadVinyls().
	 -artists/genres/loadVinyls.
	 -artists/genres/loadVinyls#
	 -artists/genres/loadVinyls().
	 -genres/loadVinyls.
	 -genres/loadVinyls#
	 -genres/loadVinyls().
	 -scala/Predef.genres.loadVinyls.
	 -scala/Predef.genres.loadVinyls#
	 -scala/Predef.genres.loadVinyls().
offset: 218
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/main.scala
text:
```scala
//> using scala 3.6.3
import scala.io.StdIn.readLine

import vinyls.*
import genres.*
import artists.*

@main
def main(): Unit = {
  var vinyls = Vinyls()
  vinyls.loadVinyls()

  var genres = Genres()
  genres.loadVin@@yls()

  var artists = Artists()
  artists.loadVinyls()

  var running: Boolean = true
  println("Welcome to the Scala Vinyl Tracking App. What would you like to access?\n")

  while(running){
    var response = null
    println("1. Vinyls\n2. Artists\n3. Genres\n4. Exit")
    readLine("> ") match {
      case "1" => vinyls.menu()
      case "2" => {
        val title = readLine("Title: ")
      }
      case "4" => running = false
      case _ => println("Invalid")
    }
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: loadVinyls.