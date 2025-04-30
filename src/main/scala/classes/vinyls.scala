import scala.io.StdIn.readLine
import scala.io.Source;
import scala.util.{Try, Success, Failure};
import java.io.{File, PrintWriter};

import functions.*
import genres.*
import artists.*

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

    class Vinyls(val artistsObject: Artists, val genresObject: Genres) {
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

        def displayVinyls(searchOption: Option[String] = None, searchCriteria: Option[String] = None): Unit = {
            if (searchOption.isEmpty || searchCriteria.isEmpty) {
                // display all vinyls
                vinyls.foreach { v =>
                    val artistNames = v.artistsIDs.flatMap(id => artistsObject.artists.find(_.artistID == id).map(_.artistName))
                    val genreNames = v.genreIDs.flatMap(id => genresObject.genres.find(_.genreID == id).map(_.genreName))

                    println(s"${v.vinylID}: ${v.vinylName}, ${v.vinylType}, ${v.releaseDate}, ${v.condition}, ${v.price}, Artists: ${artistNames.mkString(", ")}, Genres: ${genreNames.mkString(", ")}")
                }
            } else {
                // Placeholder for filtered display logic
                println("Filtered display not implemented yet.")
            }
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
                val allowedTypes = Set("ep", "lp", "single", "other")

                if (allowedTypes.contains(input.toLowerCase())) {
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
            
            val artistsIDs: List[Int] = {
                var ids = List[Int]()
                var running = true

                while (running) {
                    val input = readLine("Enter artist name (or leave blank to finish): ").trim
                    if (input.isEmpty) {
                        if (ids.nonEmpty) {
                            running = false
                        } else {
                            println("You must enter at least one genre before finishing.")
                        }
                    } else {
                        artistsObject.findArtistID(input) match {
                            case Some(id) =>
                                ids = ids :+ id
                            case None =>
                                println(s"No artist found named '$input'. Adding new artist.")
                                val newID = artistsObject.addArtist(input)
                                ids = ids :+ newID
                        }
                    }
                }

                ids
            }
            val genreIDs: List[Int] = {
                var ids = List[Int]()
                var running = true

                while (running) {
                    val input = readLine("Enter genre name (or leave blank to finish): ").trim
                    if (input.isEmpty) {
                        if (ids.nonEmpty) {
                            running = false
                        } else {
                            println("You must enter at least one genre before finishing.")
                        }
                    } else {
                        genresObject.findGenreID(input) match {
                            case Some(id) =>
                                ids = ids :+ id
                            case None =>
                                println(s"No genre found named '$input'. Adding new genre.")
                                val newID = genresObject.addGenre(input)
                                ids = ids :+ newID
                        }
                    }
                }

                ids
            }


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
            // save vinyls to file
            try {
                val writer = new PrintWriter(file.toIO)

                // for each vinyl, save in file in correct format
                vinyls.foreach { v =>
                    val vinylData = s"{${v.vinylID}, \"${v.vinylName}\", \"${v.vinylType}\", \"${v.releaseDate}\", \"${v.condition}\", ${v.price}, [${v.artistsIDs.mkString(", ")}], [${v.genreIDs.mkString(", ")}]}"
                    writer.println(vinylData)  // write vniyl data to file in the correct way that will be loaded
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
                    val lines = source.getLines().toList
                    source.close()

                    vinyls = lines.flatMap { line =>
                        val cleanedLine = line.trim.stripPrefix("{").stripSuffix("}")

                        // this regex splits the line using commas, it has a negative lookahead to make sure that it is only using commas outside of quotation marks.
                        val section = cleanedLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim)

                        // Check if the line has exactly 8 section (vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs)
                        if (section.length == 8) {
                            try {
                                val vinylID = section(0).toInt
                                val vinylName = section(1).stripPrefix("\"").stripSuffix("\"")
                                val vinylType = section(2).stripPrefix("\"").stripSuffix("\"")
                                val releaseDate = section(3).stripPrefix("\"").stripSuffix("\"")
                                val condition = section(4).stripPrefix("\"").stripSuffix("\"")
                                val price = section(5).toFloat

                                // parse the artistsIDs and genreIDs (split by commas and convert to List of Ints)
                                val artistsIDs = if (section(6).trim == "[]") {
                                    List()
                                }
                                else {
                                    section(6).split(",").map(_.trim.toInt).toList
                                }
                                
                                val genreIDs = if (section(7).trim == "[]") {
                                    List()
                                }
                                else {
                                    section(7).split(",").map(_.trim.toInt).toList
                                }

                                // create a vinyl object
                                Some(Vinyl(vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs))
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