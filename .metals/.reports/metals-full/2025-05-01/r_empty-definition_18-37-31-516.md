error id: getOrElse.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/genres.scala
empty definition using pc, found symbol in pc: getOrElse.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -.
	 -#
	 -().
	 -scala/Predef.
	 -scala/Predef#
	 -scala/Predef().
offset: 1693
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/genres.scala
text:
```scala
import scala.io.StdIn.readLine
import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{File, PrintWriter}

package genres{

    case class Genre(
        val genreID: Int,
        var genreName: String
    )

    class Genres(){
        var genres: List[Genre] = List()

        // used in the save and load genres function
        private val path: os.Path = os.pwd / "resources"
        private var file: os.Path = path / "genres.txt"

        def menu(): Unit = {
            var running = true

            while (running) {
                // display genre menu
                println(
                    "\n1. View Genres" +
                    "\n2. Add Genre" +
                    "\n3. Edit Genre" +
                    "\n4. Remove Genre" +
                    "\n5. Back")

                // ask user for input and reach functionality if valid.
                readLine("> ") match {
                    // view genres
                    case "1" =>
                        println("Viewing genres...")
                        displayGenres()

                    // add genre
                    case "2" =>
                        val name = readLine("Enter new genre name: ").trim
                        if (name.nonEmpty) {
                            addGenre(name)
                            println("Genre added.")
                        } else {
                            println("Genre name cannot be empty.")
                        }

                    // edit genre
                    case "3" =>
                        val id = readLine("Enter vinyl name to edit: ").trim
                        editVinyl(findVinylID(id).getOrEls@@e(-1))

                    // remove genre
                    case "4" =>
                        removeGenre()

                    // back
                    case "5" =>
                        running = false // will stop this menu from running, going back to main.scala

                    // invalid
                    case _ =>
                        println("Invalid option.")
                }
            }
        }

        def addGenre(name: String): Int = {
            val genreID: Int = if (genres.isEmpty) {
                1
            } else {
                genres.map(_.genreID).max + 1
            }

            val genreName = name
            val newGenre = Genre(genreID, genreName)

            genres = genres :+ newGenre
            saveGenres()
            return genreID
        }

        def editGenre(genreID: Int): Unit = {
            // edit vinyl from vinyls
        }

        def removeGenre(genreID: Int): Unit = {
            // remove vinyl from vinyls
        }

        def saveGenres(): Unit = {
            try {
                val writer = new PrintWriter(file.toIO)
                genres.foreach { g =>
                    val genreData = s"{${g.genreID}, \"${g.genreName}\"}"
                    writer.println(genreData)
                }
                writer.close()
                println("Genres saved successfully.")
            } catch {
                case e: Exception => println(s"Error saving genres: ${e.getMessage}")
            }
        }

        def loadGenres(): Unit = {
            if (!os.exists(path)) os.makeDir(path)

            if (os.exists(file)) {
                try {
                    val source = Source.fromFile(file.toIO)
                    val lines = source.getLines().toList
                    source.close()

                    genres = lines.flatMap { line =>
                        val cleanedLine = line.trim.stripPrefix("{").stripSuffix("}")
                        val parts = cleanedLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim)

                        if (parts.length == 2) {
                            try {
                                val genreID = parts(0).toInt
                                val genreName = parts(1).stripPrefix("\"").stripSuffix("\"")
                                Some(Genre(genreID, genreName))
                            } catch {
                                case e: Exception =>
                                    println(s"Error parsing genre: ${e.getMessage}")
                                    None
                            }
                        } else {
                            println(s"Invalid genre format: $line")
                            None
                        }
                    }

                    println(s"Loaded ${genres.length} genres.")
                } catch {
                    case e: Exception =>
                        println(s"Error loading genres: ${e.getMessage}")
                }
            } else {
                os.write(file, "")
                println("Created new genres file.")
            }
        }


        def findGenreID(name: String): Option[Int] = {
            genres.find(_.genreName.toLowerCase() == name.toLowerCase()).map(_.genreID)
        }
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: getOrElse.