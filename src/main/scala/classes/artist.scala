import scala.io.StdIn.readLine

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

        def loadArtists(path: os.Path): Unit = {
            // load Artists from file, if empty, create file
        }

        def findArtistID(name: String): Option[Int] = {
            artists.find(_.artistName == name).map(_.artistID)
            // var artistID: Option[Int] = None
            // artists.foreach { artist =>
            //     if (name == artist.artistName) {
            //         artistID = Some(artist.artistID)
            //     }
            // }
            // artistID
        }
    }
}