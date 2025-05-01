import scala.io.StdIn.readLine
import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{File, PrintWriter}

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
                    case "2" =>
                        println("Search by: name, artist, genre, type, price, date")
                        val field = readLine("> ").trim.toLowerCase

                        field match {
                            case "name" | "artist" | "genre" | "type" =>
                                val query = readLine(s"Enter value to search in $field: ").trim.toLowerCase
                                displayVinyls(Some(field), Some(query))

                            case "price" =>
                                val sortOrder = readLine("Sort by smallest (cheapest) or largest (most expensive): ").trim.toLowerCase
                                displayVinyls(Some(field), Some(sortOrder))

                            case "date" =>
                                val sortOrder = readLine("Sort by smallest (oldest) or largest (newest): ").trim.toLowerCase
                                displayVinyls(Some(field), Some(sortOrder))
                                
                            case _ =>
                                println("Invalid search field.")
                        }

                    case "3" => addVinyl()
                    case "4" =>
                        val id = readLine("Enter vinyl name to edit: ").trim

                        editVinyl(findVinylID(id).getOrElse(-1))

                    case "5" =>
                        val id = readLine("Enter vinyl name to remove: ").trim
                        removeVinyl(findVinylID(id).getOrElse(-1))
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
                val filtered = (searchOption.get, searchCriteria.get.toLowerCase) match {
                    case ("name", query) =>
                        vinyls.filter(_.vinylName.toLowerCase.contains(query))

                    case ("artist", query) =>
                        vinyls.filter(v => 
                            v.artistsIDs.exists(id => 
                                artistsObject.artists.find(_.artistID == id).exists(_.artistName.toLowerCase.contains(query))
                            )
                        )

                    case ("genre", query) =>
                        vinyls.filter(v =>
                            v.genreIDs.exists(id =>
                                genresObject.genres.find(_.genreID == id).exists(_.genreName.toLowerCase.contains(query))
                            )
                        )

                    case ("type", query) =>
                        vinyls.filter(_.vinylType.toLowerCase.contains(query))

                    case ("price", "smallest") =>
                        vinyls.sortBy(_.price)

                    case ("price", "largest") =>
                        vinyls.sortBy(_.price).reverse

                    case ("date", "smallest") =>
                        vinyls.sortBy(_.releaseDate)

                    case ("date", "largest") =>
                        vinyls.sortBy(_.releaseDate).reverse

                    case _ =>
                        println("Invalid search.")
                        vinyls
                }

                // Print filtered or sorted vinyls
                if (filtered.isEmpty) {
                    println(s"No vinyls found matching your search query.")
                } else {
                    filtered.foreach { v =>
                        val artistNames = v.artistsIDs.flatMap(id => artistsObject.artists.find(_.artistID == id).map(_.artistName))
                        val genreNames = v.genreIDs.flatMap(id => genresObject.genres.find(_.genreID == id).map(_.genreName))

                        println(s"${v.vinylID}: ${v.vinylName}, ${v.vinylType}, ${v.releaseDate}, ${v.condition}, ${v.price}, Artists: ${artistNames.mkString(", ")}, Genres: ${genreNames.mkString(", ")}")
                    }
                }
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
                    Some(input.trim) 
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

            val releaseDate = askUntilValid("Release Date (DD-MM-2002): ") { input =>
                if (input.matches("\\d{2}-\\d{2}-\\d{4}")) {
                    Some(input) 
                } else {
                    None
                }
            }

            val condition = askUntilValid("Condition (New, Good, Worn, etc.): ") { input =>
                if (input.nonEmpty) {
                    Some(input.trim) 
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


        def editVinyl(vinylID: Int): Unit = {
            vinyls.find(_.vinylID == vinylID) match {
                case Some(vinyl) =>
                    var editing = true
                    while (editing) {
                        println(
                            s"Editing Vinyl ID ${vinyl.vinylID}: ${vinyl.vinylName}\n" +
                            "What would you like to edit?\n" +
                            "1. Name\n2. Type\n3. Release Date\n4. Condition\n5. Price\n6. Add Artist\n7. Add Genre\n8. Done"
                        )
                        readLine("> ") match {
                            case "1" =>
                                val name = readLine("Enter new vinyl name: ").trim
                                if (name.nonEmpty) vinyl.vinylName = name
                            case "2" =>
                                val typ = readLine("Enter new type (EP, LP, Single, Other): ").trim.capitalize
                                val valid = Set("EP", "LP", "Single", "Other")
                                if (valid.contains(typ)) vinyl.vinylType = typ
                                else println("Invalid type.")
                            case "3" =>
                                val date = readLine("Enter new release date (YYYY-MM-DD): ").trim
                                if (date.matches("\\d{2}-\\d{2}-\\d{4}")) vinyl.releaseDate = date
                                else println("Invalid date format.")
                            case "4" =>
                                val cond = readLine("Enter new condition: ").trim
                                if (cond.nonEmpty) vinyl.condition = cond
                            case "5" =>
                                val priceStr = readLine("Enter new price: ").trim
                                try {
                                    vinyl.price = priceStr.toFloat
                                } catch {
                                    case _: Exception => println("Invalid price.")
                                }
                            case "6" =>
                                val input = readLine("Enter artist name to add: ").trim
                                artistsObject.findArtistID(input) match {
                                    case Some(id) =>
                                        if (!vinyl.artistsIDs.contains(id)) {
                                            vinyl.artistsIDs :+= id
                                            println("Artist added.")
                                        } else {
                                            println("Artist already on vinyl.")
                                        }
                                    case None =>
                                        val newID = artistsObject.addArtist(input)
                                        vinyl.artistsIDs :+= newID
                                        println("New artist added.")
                                        println("Artist added.")
                                }
                            case "7" =>
                                val input = readLine("Enter genre name to add: ").trim
                                genresObject.findGenreID(input) match {
                                    case Some(id) =>
                                        if (!vinyl.genreIDs.contains(id)) {
                                            vinyl.genreIDs :+= id
                                            println("Genre added.")
                                        } else {
                                            println("Genre already on vinyl.")
                                        }
                                    case None =>
                                        val newID = genresObject.addGenre(input)
                                        vinyl.genreIDs :+= newID
                                        println("New genre added.")
                                        println("Genre added.")
                                }
                            case "8" =>
                                editing = false
                                saveVinyls()
                                println("Changes saved.")
                            case _ =>
                                println("Invalid option.")
                        }
                    }

                case None =>
                    println(s"No vinyl found with ID $vinylID.")
            }
        }


        def removeVinyl(vinylID: Int): Unit = {
            val vinylOpt = vinyls.find(_.vinylID == vinylID)
            vinylOpt match {
                case Some(v) =>
                    println(s"Are you sure you want to remove '${v.vinylName}'? (y/n)")
                    val confirm = readLine("> ").trim.toLowerCase
                    if (confirm == "y" || confirm == "yes") {
                        vinyls = vinyls.filterNot(_.vinylID == vinylID)
                        saveVinyls()
                        println("Vinyl removed and saved.")
                    } else {
                        println("Remove cancelled.")
                    }
                case None =>
                    println(s"No vinyl found with ID $vinylID.")
            }
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
                        try {
                            val cleanedLine = line.trim.stripPrefix("{").stripSuffix("}")

                            // Manually extract fields by index using a small state machine approach
                            val parts = scala.collection.mutable.ArrayBuffer[String]()
                            val sb = new StringBuilder
                            var inQuotes = false
                            var inBrackets = 0

                            for (c <- cleanedLine) {
                                c match {
                                    case '"' => 
                                        inQuotes = !inQuotes
                                        sb.append(c)
                                    case '[' => 
                                        inBrackets += 1
                                        sb.append(c)
                                    case ']' => 
                                        inBrackets -= 1
                                        sb.append(c)
                                    case ',' if !inQuotes && inBrackets == 0 =>
                                        parts += sb.toString().trim
                                        sb.clear()
                                    case _ => 
                                        sb.append(c)
                                }
                            }
                            parts += sb.toString().trim  // Add last field

                            if (parts.length != 8) {
                                println(s"Invalid line format: $line")
                                None
                            } else {
                                val vinylID = parts(0).toInt
                                val vinylName = parts(1).stripPrefix("\"").stripSuffix("\"")
                                val vinylType = parts(2).stripPrefix("\"").stripSuffix("\"")
                                val releaseDate = parts(3).stripPrefix("\"").stripSuffix("\"")
                                val condition = parts(4).stripPrefix("\"").stripSuffix("\"")
                                val price = parts(5).toFloat

                                val artistsIDs = if (parts(6) == "[]") List()
                                                else parts(6).stripPrefix("[").stripSuffix("]").split(",").map(_.trim.toInt).toList

                                val genreIDs = if (parts(7) == "[]") List()
                                            else parts(7).stripPrefix("[").stripSuffix("]").split(",").map(_.trim.toInt).toList

                                Some(Vinyl(vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs))
                            }
                        } catch {
                            case e: Exception =>
                                println(s"Error parsing line: $line\n${e.getMessage}")
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

        def findVinylID(name: String): Option[Int] = {
            vinyls.find(_.vinylName.toLowerCase() == name.toLowerCase()).map(_.vinylID)
        }
    }
}