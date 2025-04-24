import scala.io.StdIn.readLine
import scala.compiletime.uninitialized

package artists{

    case class Artist(
        val artistID: Int,
        var artistName: String,
        var artistDOB: String
    )

    class Artists(){
        var artists: List[Artist] = List()

        def menu(): Unit = {
            println("1. View Artists" +
            "\n2. Artists" +
            "\n3. Artists" +
            "\n4. Exit")
        }

        def displayArtists(searchCriteria: String = null): Unit = {
            
        }

        def addArtist(): Unit = {
            // add Artist
        }

        def editArtist(): Unit = {
            // edit Artist from Artists
        }

        def removeArtist(): Unit = {
            // remove Artist from Artists
        }

        def saveArtists(): Unit = {
            // save Artists to file
        }

        def loadArtists(): Unit = {
            // load Artists from file, if empty, create file
        }

        def findArtistID(name: String): Unit = {
            var artistID: Int = uninitialized
            artists.foreach { artist =>
                
                if name == artist.artistName then artistID = artist.artistID
            }
            return artistID
        }
    }
}