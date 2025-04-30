import scala.io.StdIn.readLine

package artists{

    case class Artist(
        val artistID: Int,
        var artistName: String,
        var artistDOB: String
    )

    class Artists(){
        var artists: List[Artist] = List()

        // used in the save and load genres function
        private val path: os.Path = os.pwd / "resources"
        private var file: os.Path = path / "artists.txt"

        def menu(): Unit = {
            println("1. View Artists" +
            "\n2. Artists" +
            "\n3. Artists" +
            "\n4. Exit")
        }

        def displayArtists(searchCriteria: Option[String] = None): Unit = {
            
        }

        def addArtist(name: String, dob: Option[String] = None): Int = {
            val artistID: Int = if (artists.isEmpty) {
                1
            } else {
                artists.map(_.artistID).max + 1
            }
            
            val artistName = name
            val artistDOB = dob.getOrElse("unknown")
            val newArtist = Artist(artistID, artistName, artistDOB)
            
            artists = artists :+ newArtist
            saveArtists()
            return artistID
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
            val path: os.Path = os.pwd / "resources"
            var file: os.Path = path / "artists.json"
            if (!os.exists(path)) then
                // create directory if doesnt exist
                os.makeDir(path)

            if (os.exists(file)) then {
                println("loaded file")
            } else {
                os.write(file, "")
                println("Created new artits file.")
            }
        }

        def findArtistID(name: String): Option[Int] = {
            artists.find(_.artistName.toLowerCase() == name.toLowerCase()).map(_.artistID)
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