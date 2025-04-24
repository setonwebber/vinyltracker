import scala.io.StdIn.readLine
import scala.compiletime.uninitialized

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

        def loadGenres(): Unit = {
            // load vinyls from file, if empty, create file
        }

        def findGenreID(name: String): Unit = {
            var genreID: Int = uninitialized
            genres.foreach { genre=>
                if name == genre.genreName then genreID = genre.genreID
            }
            return genreID
        }
    }
}