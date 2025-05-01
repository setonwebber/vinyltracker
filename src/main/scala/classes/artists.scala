import scala.io.StdIn.readLine
import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{File, PrintWriter}

package artists {

    // Artist case class represents a single artist with a unique ID, name, and date of birth
    case class Artist(
        val artistID: Int,
        var artistName: String,
        var artistDOB: String
    )

    // Artists class acts as the manager of the Artist case class, handles all artist-related operations
    class Artists() {
        // list of artist objects that will be used
        var artists: List[Artist] = List()

        // used in the save and load artists function
        private val path: os.Path = os.pwd / "resources"
        private var file: os.Path = path / "artists.txt"

        // menu function called from main.scala, inside gives all the functionality from the artists class
        def menu(): Unit = {
            var running = true

            while (running) {
                // display artist menu
                println(
                    "\n1. View Artists" +
                    "\n2. Add Artist" +
                    "\n3. Edit Artist" +
                    "\n4. Back")

                // ask user for input and reach functionality if valid.
                readLine("> ") match {
                    // display artists
                    case "1" =>
                        println("Viewing artists...")
                        displayArtists()

                    // add artist
                    case "2" =>
                        val name = readLine("Enter artist name: ").trim
                        if (name.nonEmpty) {
                            val dob = readLine("Enter artist DOB (YYYY-MM-DD or 'unknown'): ").trim
                            addArtist(name, Some(dob))
                            println("Artist added.")
                        } else {
                            println("Artist name cannot be empty.")
                        }

                    // edit artist
                    case "3" =>
                        val id = readLine("Enter artist name to edit: ").trim
                        editArtist(findArtistID(id).getOrElse(-1))

                    // back
                    case "4" =>
                        running = false // will stop this menu from running, going back to main.scala

                    // invalid input
                    case _ =>
                        println("Invalid option.")
                }
            }
        }

        // displayArtists() function prints every artist in the list
        def displayArtists(): Unit = {
            if (artists.isEmpty) {
                println("No artists available.")
            } else {
                println("Artists:")
                artists.foreach { a =>
                    println(s"ID: ${a.artistID}, Name: ${a.artistName}, DOB: ${a.artistDOB}")
                }
            }
        }

        // addArtist() function creates a new artist with a unique ID and saves it
        def addArtist(name: String, dob: Option[String] = None): Int = {
            val artistID: Int = if (artists.isEmpty) {
                1
            } else {
                // artistID is set to max existing ID + 1 to avoid duplicates
                artists.map(_.artistID).max + 1
            }

            val artistName = name
            val artistDOB = dob.getOrElse("unknown")
            val newArtist = Artist(artistID, artistName, artistDOB)

            artists = artists :+ newArtist
            saveArtists()
            return artistID
        }

        // editArtist() function allows updating artist name or DOB via a selection menu, like vinyls
        def editArtist(artistID: Int): Unit = {
            // find artist attached to artistID
            artists.find(_.artistID == artistID) match {
                case Some(artist) =>
                    // if artist exists, start editing
                    var editing = true
                    while (editing) {
                        println(
                            s"Editing Artist ID ${artist.artistID}: ${artist.artistName} (DOB: ${artist.artistDOB})\n" +
                            "What would you like to edit?\n" +
                            "1. Name\n2. DOB\n3. Done"
                        )

                        // ask user which field to edit
                        readLine("> ") match {
                            case "1" =>
                                val newName = readLine("Enter new artist name: ").trim
                                if (newName.nonEmpty) {
                                    artist.artistName = newName
                                    println("Artist name updated.")
                                } else {
                                    println("Name cannot be empty.")
                                }

                            case "2" =>
                                val newDOB = readLine("Enter new artist DOB (YYYY-MM-DD or 'unknown'): ").trim
                                if (newDOB.nonEmpty) {
                                    artist.artistDOB = newDOB
                                    println("Artist DOB updated.")
                                } else {
                                    println("DOB cannot be empty.")
                                }

                            case "3" =>
                                // exit editing loop and save
                                editing = false
                                saveArtists()
                                println("Changes saved.")

                            case _ =>
                                println("Invalid option.")
                        }
                    }

                case None =>
                    // if no artist is found
                    println(s"No artist found with ID $artistID.")
            }
        }

        // saveArtists() function writes the current artist list to the file
        def saveArtists(): Unit = {
            try {
                val writer = new PrintWriter(file.toIO)
                artists.foreach { a =>
                    val artistData = s"{${a.artistID}, \"${a.artistName}\", \"${a.artistDOB}\"}"
                    writer.println(artistData)
                }
                writer.close()
                // println("Artists saved successfully.")
            } catch {
                case e: Exception => println(s"Error saving artists: ${e.getMessage}")
            }
        }

        // loadArtists() function reads the artists from file and populates the artists list
        def loadArtists(): Unit = {
            // if resources folder doesn't exist, create it
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

                    // println(s"Loaded ${artists.length} artists.")
                } catch {
                    case e: Exception =>
                        println(s"Error loading artists: ${e.getMessage}")
                }
            } else {
                // if artists.txt does not exist, create it
                os.write(file, "")
                println("Created new artists file.")
            }
        }

        // findArtistID() function returns the ID of an artist by name if it exists
        def findArtistID(name: String): Option[Int] = {
            artists.find(_.artistName.toLowerCase() == name.toLowerCase()).map(_.artistID)
        }
    }
}
