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
  genres.loadGenres()

  var artists = Artists()
  artists.loadArtists()

  var running: Boolean = true
  println("Welcome to the Scala Vinyl Tracking App. What would you like to access?\n")

  while(running){
    var response = null
    println("1. Vinyls\n2. Artists\n3. Genres\n4. Exit")
    readLine("> ") match {
      case "1" => vinyls.menu()
      case "2" => artists.menu()
      case "3" => genres.menu()
      case "4" => running = false
      case _ => println("Invalid")
    }
  }
}