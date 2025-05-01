error id: `<init>`.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/artist.scala
empty definition using pc, found symbol in pc: `<init>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1433
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/artist.scala
text:
```scala
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
            try {
                val writer = ne@@w PrintWriter(file.toIO)
                artists.foreach { a =>
                    val artistData = s"{${a.artistID}, \"${a.artistName}\", \"${a.artistDOB}\"}"
                    writer.println(artistData)
                }
                writer.close()
                println("Artists saved successfully.")
            } catch {
                case e: Exception => println(s"Error saving artists: ${e.getMessage}")
            }
        }

        def loadArtists(): Unit = {
            if (!os.exists(path)) os.makeDir(path)

            if (os.exists(file)) {
                try {
                    val source = Source.fromFile(file.toIO)
                    val lines = source.getLines().toList
                    source.close()

                    artists = lines.flatMap { line =>
                        val cleanedLine = line.trim.stripPrefix("{").stripSuffix("}")
                        val parts = cleanedLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim)

                        if (parts.length == 3) {
                            try {
                                val artistID = parts(0).toInt
                                val artistName = parts(1).stripPrefix("\"").stripSuffix("\"")
                                val artistDOB = parts(2).stripPrefix("\"").stripSuffix("\"")
                                Some(Artist(artistID, artistName, artistDOB))
                            } catch {
                                case e: Exception =>
                                    println(s"Error parsing artist: ${e.getMessage}")
                                    None
                            }
                        } else {
                            println(s"Invalid artist format: $line")
                            None
                        }
                    }

                    println(s"Loaded ${artists.length} artists.")
                } catch {
                    case e: Exception =>
                        println(s"Error loading artists: ${e.getMessage}")
                }
            } else {
                os.write(file, "")
                println("Created new artists file.")
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
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<init>`.