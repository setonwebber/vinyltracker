error id: `<none>`.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 5235
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
text:
```scala
import scala.io.StdIn.readLine
import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{File, PrintWriter}

// my package imports
import functions.*
import genres.*
import artists.*

package vinyls{
    // Vinyl Case Class
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

    // Vinyls class acts as the manager of the vinyl case class, that organises and uses the vinyl objects saved.
    class Vinyls(val artistsObject: Artists, val genresObject: Genres) {
        // list of vinyl objects that will be used.
        var vinyls: List[Vinyl] = List()

        // used in the save and load vinyls function
        private val path: os.Path = os.pwd / "resources"
        private var file: os.Path = path / "vinyls.txt"

        // menu function called from main.scala, inside gives all the functionality from the vinyls class (view, add, edit, remove)
        def menu(): Unit = {
            var running = true

            while(running){
                // display vinyl menu
                println(
                    "\n1. View Vinyls" +
                    "\n2. Search Vinyls" +
                    "\n3. Add Vinyl" +
                    "\n4. Edit Vinyl" +
                    "\n5. Remove Vinyl" +
                    "\n6. Back")

                // ask user for input and reach functionality if valid.
                readLine("> ") match {
                    // display vinyls, because no input is passed through, it will display all vinyls.
                    case "1" => displayVinyls() 
                    
                    // search vinyls, with an input of search criteria and option
                    case "2" =>
                        // ask user to input the search option
                        println("Search by: name, artist, genre, type, price, date")
                        val searchOption = readLine("> ").trim.toLowerCase

                        // match the searchoption with different types of searches
                        searchOption match {
                            // these are the easiest search, as we just need to ask the user what they want to search within the names, artists, etc..
                            case "name" | "artist" | "genre" | "type" =>
                                val sortOrder = readLine(s"Enter value to search in $searchOption: ").trim.toLowerCase
                                displayVinyls(Some(searchOption), Some(sortOrder))

                            case "price" =>
                                // if the search is price or date, we search by largest or smallest
                                val sortOrder = readLine("Sort by smallest (cheapest) or largest (most expensive): ").trim.toLowerCase
                                displayVinyls(Some(searchOption), Some(sortOrder))

                            case "date" =>
                                // if the search is price or date, we search by largest or smallest
                                val sortOrder = readLine("Sort by smallest (oldest) or largest (newest): ").trim.toLowerCase
                                displayVinyls(Some(searchOption), Some(sortOrder))
                                
                            case _ =>
                                // invalid search option was inputted.
                                println("Invalid search option.")
                        }
                    
                    // add vinyl
                    case "3" => addVinyl()

                    // edit vinyl
                    case "4" =>
                        // ask the user to enter the vinyl name of the vinyl they wish to edit and run the function with the input
                        val id = readLine("Enter vinyl name to edit: ").trim
                        editVinyl(findVinylID(id).getOrElse(-1))

                    // remove vinyl
                    case "5" =>
                        // ask the user to enter the vinyl name of the vinyl they wish to remove and run the function with the input
                        val id = readLine("Enter vinyl name to remove: ").trim
                        removeVinyl(findVinylID(id).getOrElse(-1))
                    
                    // back
                    case "6" => running = false // will stop this menu from running, going back to the main.scala menu.
                    
                    case _ => println("Invalid") // invalid input
                }
            }

        }

        // displayVinyls() function called from vinyls.menu(). this can take in two inputs, but are optional (can be null); this is because this one function works for displaying vinyls and searching vinyls.
        def displayVinyls(searchOption: Option[String] = None, searchCriteria: Option[String] = None): Unit = {
            // if no searchOption or searchCriteria was inputted, display all vinyls 
            if (searchOption.isEmpty || searchCriteria.isEmpty) {
                var count: Int = 1
                // display@@ all vinyls
                vinyls.foreach { v =>
                    // get all artistsnames and genrenames from the artistIDs and genreIDs values

                    // this function searches through each ID in the artists list (from artists.scala) to find the corresponding name attached to the same id
                    val artistNames = v.artistsIDs.flatMap(id => artistsObject.artists.find(_.artistID == id).map(_.artistName))
                    // this function searches through each ID in the genres list (from genres.scala) to find the corresponding name attached to the same id
                    val genreNames = v.genreIDs.flatMap(id => genresObject.genres.find(_.genreID == id).map(_.genreName))

                    println(s"${count}: ${v.vinylName}, ${v.vinylType}, ${v.releaseDate}, ${v.condition}, ${v.price}, Artists: ${artistNames.mkString(", ")}, Genres: ${genreNames.mkString(", ")}")
                    // iterate count (vinylid is not used as its not a valid indicator of which the order of vinyls)
                    count = count + 1
                }
            } else {
                // create a filtered array. this will be an array of vinyls that are filtered out with the search option and criteria
                val filtered = (searchOption.get, searchCriteria.get.toLowerCase) match {
                    // if the searchOption is name, filter out searchCriteria by query in each name
                    case ("name", query) =>
                        vinyls.filter(_.vinylName.toLowerCase.contains(query)) // initialises filtered as vinyls filtered

                    // if the searchOption is artist, filter out searchCriteria by query in each artist for each vinyl
                    case ("artist", query) =>
                        vinyls.filter(v => 
                            v.artistsIDs.exists(id => 
                                artistsObject.artists.find(_.artistID == id).exists(_.artistName.toLowerCase.contains(query))
                            )
                        ) // initialises filtered as vinyls filtered

                    // if the searchOption is genre, filter out searchCriteria by query in each genre for each vinyl
                    case ("genre", query) =>
                        vinyls.filter(v =>
                            v.genreIDs.exists(id =>
                                genresObject.genres.find(_.genreID == id).exists(_.genreName.toLowerCase.contains(query))
                            )
                        ) // initialises filtered as vinyls filtered

                    // if the searchOption is type, filter out searchCriteria by query in each type
                    case ("type", query) =>
                        vinyls.filter(_.vinylType.toLowerCase.contains(query)) // initialises filtered as vinyls filtered

                    // if the searchOption is price and searchCriteria is "smallest", sort vinyls by price ascending (cheapest first)
                    case ("price", "smallest") =>
                        vinyls.sortBy(_.price) // initialises filtered as vinyls sorted
                    
                    // if the searchOption is price and searchCriteria is "largest", sort vinyls by price descending (most expensive first)
                    case ("price", "largest") =>
                        vinyls.sortBy(_.price).reverse // initialises filtered as vinyls sorted

                    // if the searchOption is date and searchCriteria is "smallest", sort vinyls by date ascending (newest first)
                    case ("date", "smallest") =>
                        vinyls.sortBy(_.releaseDate) // initialises filtered as vinyls sorted
                    
                    // if the searchOption is date and searchCriteria is "largest", sort vinyls by date descending (oldest first)
                    case ("date", "largest") =>
                        vinyls.sortBy(_.releaseDate).reverse // initialises filtered as vinyls sorted

                    case _ =>
                        // invalid search queries, return vinyl list
                        println("Invalid search.")    
                        vinyls
                }

                // print filtered or sorted vinyls
                if (filtered.isEmpty) {
                    // if no vinyls are returned.
                    println(s"No vinyls found matching your search query.")
                } else {
                    // display all vinyls again, but using filtered
                    var count: Int = 1
                    filtered.foreach { v =>
                        val artistNames = v.artistsIDs.flatMap(id => artistsObject.artists.find(_.artistID == id).map(_.artistName))
                        val genreNames = v.genreIDs.flatMap(id => genresObject.genres.find(_.genreID == id).map(_.genreName))

                        println(s"${count}: ${v.vinylName}, ${v.vinylType}, ${v.releaseDate}, ${v.condition}, ${v.price}, Artists: ${artistNames.mkString(", ")}, Genres: ${genreNames.mkString(", ")}")
                        count = count + 1
                    }
                }
            }
        }

        // addVinyl() function called from vinyls.menu()
        def addVinyl(): Unit = {
            // if vinyls are empty vinylID = 1
            val vinylID: Int = if (vinyls.isEmpty){
                1
            } else {
                // else vinylID = the highest vinylid in vinyls + 1
                // the reason i dont use the length of the vinyl class is because if some vinyl is deleted from the Vinyls, 
                // the other vinylIDs wont update with the new ammount, meaning duplicate vinylIDs can be generated.
                vinyls.map(_.vinylID).max + 1
            }
            
            // ask the user to input each value from their vinyl, create an object with these values from the Vinyl class

            // askUntilValid() from functions.scala, uses function pointers
            val vinylName = askUntilValid("Vinyl Name: ") { input =>
                if (input.nonEmpty) {
                    Some(input) // if input is not an empty string
                } else {
                    None
                }
            }

            val vinylType = askUntilValid("Vinyl Type (EP, LP, Single, other): ") { input =>
                val allowedTypes = Set("ep", "lp", "single", "other")

                if (allowedTypes.contains(input.toLowerCase())) {
                    Some(input.capitalize) // if input is in allowed types
                } else {
                    None
                }
            }

            val releaseDate = askUntilValid("Release Date (YYYY-MM-DD): ") { input =>
                if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    Some(input) 
                    // if the input matches the regex, this regex checks if the input is 4 digits, 2 digits, and 2 digits, separated by hyphens (-)
                    // this order is mandatory for the sorting that is done in displayvinyls, where the smallest number will be the oldest
                } else {
                    None
                }
            }

            val condition = askUntilValid("Condition (New, Good, Worn, etc.): ") { input =>
                if (input.nonEmpty) {
                    Some(input) // if input is not an empty string
                } else {
                    None
                }
            }

            val price = askUntilValid("Price: ") { input =>
                try {
                    Some(input.toFloat) 
                    // if input is a float, i had to use a try catch here because its the easiest way to find a float
                    // without using a really annoying regex :(
                } catch { 
                    case _: NumberFormatException => None 
                }
            }
            
            // adding artists for the Vinyls
            val artistsIDs: List[Int] = {
                var ids = List[Int]()
                var running = true

                // because multiple artists can be on on vinyl, we will loop as many times as the user needs
                while (running) {
                    val input = readLine("Enter artist name (or leave blank to finish): ").trim
                    if (input.isEmpty) {
                        if (ids.nonEmpty) {
                            // if the input is empty(the user wants to finish) and the ids is not empty (there is atleast one artists on the vinyl)
                            // exit out of loop
                            running = false
                        } else {
                            // if the input is empty(the user wants to finish) and the ids is empty (there is no artists on the vinyl)
                            // ask user to add artist still
                            println("You must enter at least one genre before finishing.")
                        }
                    } else {
                        // artists name was entered 
                        // use findArtistID to see if the artist already exist
                        artistsObject.findArtistID(input) match {
                            case Some(id) =>
                                // artist found in Artists, add id to id list.
                                ids = ids :+ id
                            case None =>
                                // no artist exists in Artists, add new artist, add id to id list.

                                // println(s"No artist found named '$input'. Adding new artist.")
                                val newID = artistsObject.addArtist(input)
                                ids = ids :+ newID
                        }
                    }
                }
                // initalise artistIDs as ids
                ids
            }
            
            // adding genres for the Vinyls, pretty much the same as artists
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
                                // println(s"No genre found named '$input'. Adding new genre.")
                                val newID = genresObject.addGenre(input)
                                ids = ids :+ newID
                        }
                    }
                }
                // initalise genreIDs as ids
                ids
            }


            // create new Vinyl with inputted values
            val newVinyl = Vinyl(vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs)
            // add vinyl to vinyls list
            vinyls = vinyls :+ newVinyl
            // save vinyls to file
            saveVinyls()

           println("Added new vinyl: " + newVinyl.vinylName)
        }

        // editVinyl() function called from vinyls.menu() takes in a vinylID value to determine which vinyl is edited.
        def editVinyl(vinylID: Int): Unit = {
            // find vinyl attached to vinylid
            vinyls.find(_.vinylID == vinylID) match {
                case Some(vinyl) =>
                    // if vinyl exists start the editing loop
                    var editing = true
                    while (editing) {
                        println(
                            s"Editing Vinyl ID ${vinyl.vinylID}: ${vinyl.vinylName}\n" +
                            "What would you like to edit?\n" +
                            "1. Name\n2. Type\n3. Release Date\n4. Condition\n5. Price\n6. Add Artist\n7. Add Genre\n8. Done"
                        )

                        // ask the user what value theyd like to edit
                        readLine("> ") match {
                            case "1" =>
                                // ask the user what the new name for the vinyl will be
                                val vinylName = readLine("Enter new vinyl name: ").trim
                                if (vinylName.nonEmpty) {vinyl.vinylName = vinylName} // change if not empty

                            case "2" =>
                                // ask the user what the new type for the vinyl will be
                                val vinylType = readLine("Enter new type (EP, LP, Single, Other): ").trim

                                val allowedTypes = Set("ep", "lp", "single", "other")

                                if (allowedTypes.contains(vinylType.toLowerCase())) {
                                    vinyl.vinylType = vinylType // change if valid type
                                } else {
                                    println("Invalid type.")
                                }    

                            case "3" =>
                                // ask the user what the new date for the vinyl will be
                                val releaseDate = readLine("Enter new release date (YYYY-MM-DD): ").trim
                                if (releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                    vinyl.releaseDate = releaseDate // change if valid date
                                } else {
                                    println("Invalid date format.")
                                }
                                    
                            case "4" =>
                                // ask the user what the new condition for the vinyl will be
                                val condition = readLine("Enter new condition: ").trim
                                if (condition.nonEmpty) {vinyl.condition = condition} // change if not empty

                            case "5" =>
                                // ask the user what the new price for the vinyl will be
                                val price = readLine("Enter new price: ").trim
                                try {
                                    vinyl.price = price.toFloat // change if toFloat works (if not, it's not a number likely)
                                } catch {
                                    case _: Exception => println("Invalid price.")
                                }

                            case "6" =>
                                // ask the user for the name of an artist to add to vinyl
                                val input = readLine("Enter artist name to add: ").trim
                                
                                // use findArtistID to see if the artist already exist
                                artistsObject.findArtistID(input) match {
                                    case Some(id) =>
                                        if (!vinyl.artistsIDs.contains(id)) {
                                            // if artist exists and isnt on the vinyl already
                                            vinyl.artistsIDs :+= id
                                            println("Artist added.")
                                        } else {
                                            // if artists is already on the vinyl
                                            println("Artist already on vinyl.")
                                        }
                                    case None =>
                                        // no artist exists in Artists, add new artist, add id to aristsIDs list.
                                        val newID = artistsObject.addArtist(input)
                                        vinyl.artistsIDs :+= newID
                                        // println("New artist added.")
                                        println("Artist added.")
                                }
                            
                            case "7" =>
                                // ask the user for the name of an genre to add to vinyl
                                val input = readLine("Enter genre name to add: ").trim

                                // use findGenreID to see if the genre already exist
                                genresObject.findGenreID(input) match {
                                    case Some(id) =>
                                        if (!vinyl.genreIDs.contains(id)) {
                                            // if genre exists and isnt on the vinyl already
                                            vinyl.genreIDs :+= id
                                            println("Genre added.")
                                        } else {
                                            // if genre is already on the vinyl
                                            println("Genre already on vinyl.")
                                        }
                                    case None =>
                                        // no genre exists in Genres, add new genre, add id to genreIDs list.
                                        val newID = genresObject.addGenre(input)
                                        vinyl.genreIDs :+= newID
                                        // println("New genre added.")
                                        println("Genre added.")
                                }

                            case "8" =>
                                // exit editing loop and save vinyls
                                editing = false
                                saveVinyls()
                                println("Changes saved.")

                            case _ =>
                                println("Invalid option.")
                        }
                    }

                case None =>
                    // if no vinyl is found
                    println(s"No vinyl found with ID $vinylID.")
            }
        }

        // removeVinyl() function called from vinyls.menu(). takes in a vinylID value to determine which vinyl is removed.
        def removeVinyl(vinylID: Int): Unit = {
            // find vinyl attached to id
            vinyls.find(_.vinylID == vinylID) match {
                case Some(v) =>
                    // if vinyl is returned, ask the user again if they want to delete the correct vinyl
                    println(s"Are you sure you want to remove '${v.vinylName}'? (y/n)")

                    val confirm = readLine("> ").trim.toLowerCase
                    if (confirm == "y" || confirm == "yes") {
                        // if vinyl deletion is confirmed
                        // we have to filter the vinyls list with every vinyl excluding the one attached to the id, and revalue the vinyls list as the new list. strange way of doing it in scala
                        vinyls = vinyls.filterNot(_.vinylID == vinylID)
                        // save vinyls
                        saveVinyls()
                        println("Vinyl removed.")
                    } else {
                        // if vinyl deletion is cancelled
                        println("Remove cancelled.")
                    }
                case None =>
                    println(s"No vinyl found with ID $vinylID.")
            }
        }

        // saveVinyls() function called from vinyls.menu()
        def saveVinyls(): Unit = {
            // save vinyls to file
            try {
                val writer = new PrintWriter(file.toIO)

                // for each vinyl, save in file in correct format
                vinyls.foreach { v =>
                    // this format ensures that the vinyls are saved in the exact way that needs to be loaded, it does this by separating each value by commas, and lists by brackets. the entire line is encased in curly brackets
                    val vinylData = s"{${v.vinylID}, \"${v.vinylName}\", \"${v.vinylType}\", \"${v.releaseDate}\", \"${v.condition}\", ${v.price}, [${v.artistsIDs.mkString(", ")}], [${v.genreIDs.mkString(", ")}]}"
                
                    writer.println(vinylData)  // write vniyl data to file (at a new line) in the correct way that will be loaded with loadVinyls()
                }
                writer.close()
                // println("Vinyls saved successfully in .txt file.")
            } catch {
                // catch exception just in case.
                case e: Exception => println(s"Error saving vinyls: ${e.getMessage}")
            }
        }
        // loadVinyls() function called from vinyls.menu()
        def loadVinyls(): Unit = {
            // if the folder to the vinyl.txt file doesnt exist, create one
            if (!os.exists(path)) then
                os.makeDir(path)

            // if the vinyls.txt file exists
            if (os.exists(file)) {
                try {
                    // open file and get each line as a list in a lines List()
                    val source = Source.fromFile(file.toIO)
                    val lines = source.getLines().toList
                    source.close()

                    // for each line in lines, return a new vinyl created with the details in the line.
                    vinyls = lines.flatMap { line =>
                        try {
                            // strip the brackets of the end and start of each line
                            val cleanedLine = line.trim.stripPrefix("{").stripSuffix("}")

                            // this part will be talked about in detail in the report, importing files to objects/lists is very annoying in scala and is one of the major downsides ive found
                            // this can be done with an external library such as uPickle, but i didnt want to include any imports in my project
                            // so enjoy this really weird extracting method that i just rushed together using stringbuilders and a kinda state machine approach
                            
                            // each vinyl will be saved in parts
                            val parts = scala.collection.mutable.ArrayBuffer[String]()
                            val sb = new StringBuilder
                            var inQuotes = false
                            var inBrackets = 0

                            // for each char in cleanedLine
                            for (c <- cleanedLine) {
                                c match {
                                    case '"' => 
                                        // set inQuotes to positive (if its negative) or to negative (if its positive)
                                        inQuotes = !inQuotes
                                        // append char to stringbuilder
                                        sb.append(c)
                                    case '[' => 
                                        // increaes in brackets by 1, using an int and not a bool because can have multiple in brackets (for example (this))
                                        inBrackets += 1
                                        // append char to stringbuilder
                                        sb.append(c)
                                    case ']' => 
                                        // decreases in brackets by 1
                                        inBrackets -= 1
                                        // append char to stringbuilder
                                        sb.append(c)
                                    case ',' if !inQuotes && inBrackets == 0 =>
                                        // if met with a comma that is outside of a bracket or quotations, add current string worked on to the current ArrayBuffer of parts
                                        parts += sb.toString().trim
                                        // clear stringbuilder to make room for the next value
                                        sb.clear()
                                    case _ => 
                                        // if anything else (letters, numbers, other punctuation)
                                        // append char to stringbuilder
                                        sb.append(c)
                                }
                            }
                            parts += sb.toString().trim  // add the last field on the line
                            // this chunk of code separates everything into individual values inside of the parts array, that can then be loaded into vinyls

                            if (parts.length != 8) {
                                // if parts is the right length, it needs 8 values
                                println(s"Invalid line format: $line")
                                None
                            } else {
                                // initalise all parts into variables to create a vinyl object
                                val vinylID = parts(0).toInt
                                val vinylName = parts(1).stripPrefix("\"").stripSuffix("\"")
                                val vinylType = parts(2).stripPrefix("\"").stripSuffix("\"")
                                val releaseDate = parts(3).stripPrefix("\"").stripSuffix("\"")
                                val condition = parts(4).stripPrefix("\"").stripSuffix("\"")
                                val price = parts(5).toFloat

                                val artistsIDs = if (parts(6) == "[]") {
                                    List() // if part indicates an empty list, create empty list
                                } else {
                                    // remove brackets and split each integer by comma, initalise artistsIDs to list
                                    parts(6).stripPrefix("[").stripSuffix("]").split(",").map(_.trim.toInt).toList
                                }

                                val genreIDs = if (parts(7) == "[]") {
                                    List() // if part indicates an empty list, create empty list
                                } else {
                                    // remove brackets and split each integer by comma, initalise genreIDs to list
                                    parts(7).stripPrefix("[").stripSuffix("]").split(",").map(_.trim.toInt).toList
                                }
                                
                                // attempt to make a vinyl object out of the values this adds it to the vinyls list
                                Some(Vinyl(vinylID, vinylName, vinylType, releaseDate, condition, price, artistsIDs, genreIDs))
                            }
                        } catch {
                            case e: Exception =>
                                println(s"Error parsing line: $line\n${e.getMessage}")
                                None
                        }
                    }

                    // println(s"Loaded ${vinyls.length} vinyls from the file.")
                } catch {
                    case e: Exception =>
                        println(s"Error loading vinyls file: ${e.getMessage}")
                }
            } else {
                // if the vinyls.txt file doesnt exist
                try {
                    // create an empty vinyls.txt file
                    os.write(file, "")

                    // println("Created new vinyls file.")
                } catch {
                    case e: Exception =>
                        println(s"Error creating vinyls file: ${e.getMessage}")
                }
            }
        }

        // findVinylID() function
        def findVinylID(name: String): Option[Int] = {
            // this will return the vinylID of the vinyl that is found with the same name as the input string.
            vinyls.find(_.vinylName.toLowerCase() == name.toLowerCase()).map(_.vinylID)
        }
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.