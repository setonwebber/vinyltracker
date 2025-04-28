error id: _empty_/`<import>`.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
empty definition using pc, found symbol in pc: _empty_/`<import>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 4
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
text:
```scala
impo@@rt scala.io.StdIn.readLine
import scala.compiletime.uninitialized

package vinyls{

    case class Vinyl(
        val vinylID: Int,
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

        def displayVinyls(searchCriteria: Option[String] = None): Unit = {
            
        }

        def addVinyl(): Unit = {
            var vinylID: Int = vinyls.length + 1
            var vinylName: String = readLine("Vinyl Name: ")
            var vinylType: String = readLine("Vinyl Type (EP, LP, Single, other): ")
            var releaseDate: String = readLine("Release Date: ")
            var condition: String = readLine("Condition: ")
            var price: Float = readLine("Price: ").toFloat
            var artistsIDs: List[Int] = List()
            // loop as many times as the user wants, search each artist in the artists list, if artist id is returned, add to artistsID list, if not, addArtist with details.
            var genreIDs: List[Int] = List()
            var v = Vinyl(vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs)
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
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/`<import>`.