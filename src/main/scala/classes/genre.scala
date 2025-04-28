import scala.io.StdIn.readLine

package genres{

    case class Genre(
        val genreID: Int,
        var genreName: String
    )

    class Genres(){
        var genres: List[Genre] = List()

        def menu(): Unit = {
            println("1. View Genres" +
            "\n2. Artists" +
            "\n3. Genres" +
            "\n4. Exit")
        }

        def displayGenres(searchCriteria: String = null): Unit = {
            
        }

        def addGenre(): Unit = {
            // add genre
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

        def loadGenres(path: os.Path): Unit = {
            // load vinyls from file, if empty, create file
        }

        def findGenreID(name: String): Option[Int] = {
            genres.find(_.genreName == name).map(_.genreID)
        }
    }
}