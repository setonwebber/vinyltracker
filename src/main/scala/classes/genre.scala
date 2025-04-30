import scala.io.StdIn.readLine

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
            // save vinyls to file
        }

        def loadGenres(): Unit = {
            val path: os.Path = os.pwd / "resources"
            var file: os.Path = path / "genres.json"
            if (!os.exists(path)) then
                // create directory if doesnt exist
                os.makeDir(path)

            if (os.exists(file)) then {
                println("loaded file")
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