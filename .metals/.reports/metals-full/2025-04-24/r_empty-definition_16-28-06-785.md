error id: scala/io/StdIn.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
empty definition using pc, found symbol in pc: scala/io/StdIn.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -scala/io/StdIn.
	 -scala/Predef.scala.io.StdIn.
offset: 16
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
text:
```scala
import scala.io.@@StdIn.readLine
package vinyls

class Vinyl (
    var vinylId: Int,
    var vinylName: String,
    var vinylType: String,
    var musicType: String,
    var releaseDate: String,
    var condition: String,
    var price: Float
){
    var artistsIDs: List[Int] = List()
    var genreIDs: List[Int] = List()
}

class Vinyls(){
    var vinyls: List[Vinyl] = List()

    def menu(): Unit = {
        println("1. View Vinyls" +
          "\n2. Artists" +
          "\n3. Genres" +
          "\n4. Exit")
    }

    def displayVinyls(searchCriteria: String = null): Unit = {
        
    }

    def addVinyl(): Unit = {
        var vinylId: Int = readLine()
        var vinylName: String
        var vinylType: String
        var musicType: String
        var releaseDate: String
        var condition: String
        var price: Float
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
```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/io/StdIn.