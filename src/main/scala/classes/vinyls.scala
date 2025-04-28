import scala.io.StdIn.readLine

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
            val vinylID: Int = vinyls.length + 1

            val vinylName = askUntilValid("Vinyl Name: ") { input =>
                if (input.nonEmpty) 
                    Some(input) 
                else 
                    None
            }

            val vinylType = askUntilValid("Vinyl Type (EP, LP, Single, other): ") { input =>
                val allowedTypes = Set("EP", "LP", "Single", "Other")

                if (allowedTypes.contains(input.capitalize)) 
                    Some(input.capitalize) 
                else 
                    None
            }

            val releaseDate = askUntilValid("Release Date (YYYY-MM-DD): ") { input =>
                if (input.matches("\\d{4}-\\d{2}-\\d{2}")) 
                    Some(input) 
                else 
                    None
            }

            val condition = askUntilValid("Condition (New, Good, Worn, etc.): ") { input =>
                if (input.nonEmpty) 
                    Some(input) 
                else 
                    None
            }

            val price = askUntilValid("Price: ") { input =>
                try 
                    Some(input.toFloat) 
                catch 
                    { case _: NumberFormatException => None }
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
            // save vinyls to file
        }

        def loadVinyls(path: os.Path): Unit = {
            var content: String = os.read(path + "vinyls.txt")
            if content:
                
            else:
                os.write(path + "vinyls.txt", "")

        }
    }
}