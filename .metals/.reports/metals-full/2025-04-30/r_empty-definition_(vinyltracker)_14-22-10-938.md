error id: flatMap.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
empty definition using pc, found symbol in pc: flatMap.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -functions/lines/flatMap.
	 -functions/lines/flatMap#
	 -functions/lines/flatMap().
	 -lines/flatMap.
	 -lines/flatMap#
	 -lines/flatMap().
	 -scala/Predef.lines.flatMap.
	 -scala/Predef.lines.flatMap#
	 -scala/Predef.lines.flatMap().
offset: 4825
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
text:
```scala
import scala.io.StdIn.readLine
import scala.io.Source;
import scala.util.{Try, Success, Failure};
import java.io.{File, PrintWriter};

import functions.*

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

        // used in the save and load vinyls function
        private val path: os.Path = os.pwd / "resources"
        private var file: os.Path = path / "vinyls.txt"

        def menu(): Unit = {
            var running = true
            while(running){
                var response = null
                println(
                    "\n1. View Vinyls" +
                    "\n2. Search Vinyls" +
                    "\n3. Add Vinyl" +
                    "\n4. Edit Vinyl" +
                    "\n5. Remove Vinyl" +
                    "\n6. Back")

                readLine("> ") match {
                    case "1" => displayVinyls()
                    case "2" => displayVinyls(/* search criteriria functionality*/ )
                    case "3" => addVinyl()
                    case "4" => editVinyl()
                    case "5" => removeVinyl()
                    case "6" => running = false
                    case _ => println("Invalid")
                }
            }

        }

        def displayVinyls(searchCriteria: Option[String] = None): Unit = {
            
        }

        def addVinyl(): Unit = {
            val vinylID: Int = if (vinyls.isEmpty){
                1
            } else {
                vinyls.map(_.vinylID).max + 1
            }

            val vinylName = askUntilValid("Vinyl Name: ") { input =>
                if (input.nonEmpty) {
                    Some(input) 
                } else {
                    None
                }
            }

            val vinylType = askUntilValid("Vinyl Type (EP, LP, Single, other): ") { input =>
                val allowedTypes = Set("EP", "LP", "Single", "Other")

                if (allowedTypes.contains(input.capitalize)) {
                    Some(input.capitalize) 
                } else {
                    None
                }
            }

            val releaseDate = askUntilValid("Release Date (YYYY-MM-DD): ") { input =>
                if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    Some(input) 
                } else {
                    None
                }
            }

            val condition = askUntilValid("Condition (New, Good, Worn, etc.): ") { input =>
                if (input.nonEmpty) {
                    Some(input) 
                } else {
                    None
                }
            }

            val price = askUntilValid("Price: ") { input =>
                try {
                    Some(input.toFloat) 
                } catch { 
                    case _: NumberFormatException => None 
                }
            }

            // We'll handle artistsIDs and genreIDs later, for now leave empty
            val artistsIDs: List[Int] = List()
            val genreIDs: List[Int] = List()

            val newVinyl = Vinyl(vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs)
            vinyls = vinyls :+ newVinyl

            println(s"Added new vinyl: $newVinyl")
            saveVinyls()
        }


        def editVinyl(): Unit = {
            // edit vinyl from vinyls
        }

        def removeVinyl(): Unit = {
            // remove vinyl from vinyls
        }

        def saveVinyls(): Unit = {
            try {
                val writer = new PrintWriter(file.toIO)

                // for each vinyl, save in file in better format
                vinyls.foreach { v =>
                    val vinylData = s"{${v.vinylID}, \"${v.vinylName}\", \"${v.vinylType}\", \"${v.releaseDate}\", \"${v.condition}\", ${v.price}, [${v.artistsIDs.mkString(", ")}], [${v.genreIDs.mkString(", ")}]}"
                    writer.println(vinylData)  // Write to the text file, one vinyl per line
                }
                writer.close()
                println("Vinyls saved successfully in .txt file.")
            } catch {
                case e: Exception => println(s"Error saving vinyls: ${e.getMessage}")
            }
        }

        def loadVinyls(): Unit = {
            if (!os.exists(path)) then
                os.makeDir(path)

            if (os.exists(file)) {
                try {
                    val source = Source.fromFile(file.toIO)
                    val content = source.mkString
                    source.close()

                    vinyls = lines.f@@latMap { line =>
                        val cleanedLine = line.trim.stripPrefix("{").stripSuffix("}")

                        // Split the line by commas, taking care of quoted values with commas inside them
                        val parts = cleanedLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim)

                        // Check if the line has exactly 8 parts (vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs)
                        if (parts.length == 8) {
                            try {
                                val vinylID = parts(0).toInt
                                val vinylName = parts(1).stripPrefix("\"").stripSuffix("\"")
                                val vinylType = parts(2).stripPrefix("\"").stripSuffix("\"")
                                val releaseDate = parts(3).stripPrefix("\"").stripSuffix("\"")
                                val condition = parts(4).stripPrefix("\"").stripSuffix("\"")
                                val price = parts(5).toFloat

                                // Parse the artistsIDs and genreIDs (split by commas and convert to List of Ints)
                                val artistsIDs = if (parts(6).trim.isEmpty) {
                                    List()
                                }
                                else {
                                    parts(6).split(",").map(_.trim.toInt).toList
                                }
                                
                                val artistsIDs = if (parts(7).trim.isEmpty) {
                                    List()
                                }
                                else {
                                    parts(7).split(",").map(_.trim.toInt).toList
                                }

                                // Create a new Vinyl object
                                Vinyl(vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs)
                            } catch {
                                case e: Exception =>
                                    println(s"Error parsing vinyl data: ${e.getMessage}")
                                    None
                            }
                        } else {
                            println(s"Invalid line format: $line")
                            None
                        }
                    }

                    println(s"Loaded ${vinyls.length} vinyls from the file.")
                } catch {
                    case e: Exception =>
                        println(s"Error loading vinyls file: ${e.getMessage}")
                }
            } else {
                try {
                    os.write(file, "")
                    println("Created new vinyls file.")
                } catch {
                    case e: Exception =>
                        println(s"Error creating vinyls file: ${e.getMessage}")
                }
            }
        }
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: flatMap.