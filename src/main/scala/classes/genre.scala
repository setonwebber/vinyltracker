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
            println("1. View Genres" +
            "\n2. Artists" +
            "\n3. Genres" +
            "\n4. Exit")
        }

        def displayGenres(searchCriteria: Option[String] = None): Unit = {
            
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


        def editGenre(): Unit = {
            // edit vinyl from vinyls
        }

        def removeGenre(): Unit = {
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