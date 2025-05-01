import scala.io.StdIn.readLine
import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{File, PrintWriter}

package genres {

    // Genre case class represents a genre object with an ID and a name
    case class Genre(
        val genreID: Int,
        var genreName: String
    )

    // Genres class manages a list of Genre objects and related functionality
    class Genres() {
        // list of genres
        var genres: List[Genre] = List()

        // used in the save and load genres function
        private val path: os.Path = os.pwd / "resources"
        private var file: os.Path = path / "genres.txt"

        // menu function called from main.scala, displays options and triggers functionality
        def menu(): Unit = {
            var running = true

            while (running) {
                // display genre menu
                println(
                    "\n1. View Genres" +
                    "\n2. Add Genre" +
                    "\n3. Edit Genre" +
                    "\n4. Back")

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
                        val id = readLine("Enter genre name to edit: ").trim
                        editGenre(findGenreID(id).getOrElse(-1))

                    // back
                    case "4" =>
                        running = false // will stop this menu from running, going back to main.scala

                    // invalid input
                    case _ =>
                        println("Invalid option.")
                }
            }
        }

        // displayGenres() function displays all genres in the list
        def displayGenres(): Unit = {
            if (genres.isEmpty) {
                println("No genres available.")
            } else {
                println("Genres:")
                genres.foreach { g =>
                    println(s"ID: ${g.genreID}, Name: ${g.genreName}")
                }
            }
        }

        // addGenre() function creates and stores a new genre, returns its ID
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

        // editGenre() function allows updating the name of an existing genre by ID
        def editGenre(genreID: Int): Unit = {
            // find genre attached to genreID
            genres.find(_.genreID == genreID) match {
                case Some(genre) =>
                    // if genre exists, start editing
                    val newName = readLine(s"Enter new name for genre '${genre.genreName}': ").trim
                    if (newName.nonEmpty) {
                        genre.genreName = newName
                        saveGenres()
                        println("Genre updated.")
                    } else {
                        println("No changes made (empty name).")
                    }
                case None =>
                    // if no genre is found
                    println(s"No genre found with ID $genreID.")
            }
        }

        // saveGenres() function saves all genres to a file
        def saveGenres(): Unit = {
            try {
                val writer = new PrintWriter(file.toIO)
                genres.foreach { g =>
                    val genreData = s"{${g.genreID}, \"${g.genreName}\"}"
                    writer.println(genreData)
                }
                writer.close()
                // println("Genres saved successfully.")
            } catch {
                case e: Exception => println(s"Error saving genres: ${e.getMessage}")
            }
        }

        // loadGenres() function loads genres from the file if it exists
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

                    // println(s"Loaded ${genres.length} genres.")
                } catch {
                    case e: Exception =>
                        println(s"Error loading genres: ${e.getMessage}")
                }
            } else {
                os.write(file, "")
                println("Created new genres file.")
            }
        }

        // findGenreID() function finds a genre by name and returns its ID, if found
        def findGenreID(name: String): Option[Int] = {
            genres.find(_.genreName.toLowerCase() == name.toLowerCase()).map(_.genreID)
        }
    }
}
