import scala.io.StdIn.readLine
import scala.compiletime.uninitialized

package vinyls{

    case class Vinyl(
        val vinylId: Int,
        var vinylName: String,
        var vinylType: String,
        var releaseDate: String,
        var condition: String,
        var price: Float,
        var artistsIDs: List[Int],
        var genreIDs: List[Int]
    )

    class Vinyls(){
        var vinyls: List[Vinyl] = List()

        def menu(): Unit = {
            println("1. View Vinyls" +
            "\n2. Artists" +
            "\n3. Genres" +
            "\n4. Exit")
        }

        def displayVinyls(searchCriteria: String = uninitialized): Unit = {
            
        }

        def addVinyl(): Unit = {
            var vinylId: Int = vinyls.length + 1
            var vinylName: String = readLine("Vinyl Name: ")
            var vinylType: String = readLine("Vinyl Type (EP, LP, Single, other): ")
            var releaseDate: String = readLine("Release Date: ")
            var condition: String = readLine("Vinyl Name: ")
            var price: Float = readLine("Vinyl Name: ")
            var artistsID: List[Int] = List()
            // loop as many times as the user wants, search each artist in the artists list, if artist id is returned, add to artistsID list, if not, addArtist with details.
            var genreIDs: List[Int] = List()
            var v = Vinyl()
        }

        def editVinyl(): Unit = {
            // edit vinyl from vinyls
        }

        def removeVinyl(): Unit = {
            // remove vinyl from vinyls
        }

        def saveVinyls(): Unit = {
            // save vinyls to file
        }

        def loadVinyls(): Unit = {
            // load vinyls from file, if empty, create file
        }
    }
}