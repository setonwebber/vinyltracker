file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala
### java.lang.AssertionError: assertion failed: position not set for runtimeChecked(<empty>) # -1 of class dotty.tools.dotc.ast.Trees$Apply in C:/Users/seton/OneDrive/School/FINALSEM LETS GOOOOOOOO/Programming Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1762
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
        private var file: os.Path = path / "vinyls.json"

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
                vinyls.map(_.@@)
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
            try {
                val writer = new PrintWriter(file.toIO)
                val vinylsJson = vinyls.map { v =>
                    s"""{
  "vinylID": ${v.vinylID},
  "vinylName": "${v.vinylName}",
  "vinylType": "${v.vinylType}",
  "releaseDate": "${v.releaseDate}",
  "condition": "${v.condition}",
  "price": ${v.price},
  "artistsIDs": [${v.artistsIDs.mkString(", ")}],
  "genreIDs": [${v.genreIDs.mkString(", ")}]
}""".stripMargin
                }.mkString("[\n", ",\n", "\n]")

                writer.write(vinylsJson)
                writer.close()
                println("Vinyls saved successfully.")
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



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:8)
	dotty.tools.dotc.typer.Typer$.assertPositioned(Typer.scala:76)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3657)
	dotty.tools.dotc.typer.Applications.extMethodApply(Applications.scala:2642)
	dotty.tools.dotc.typer.Applications.extMethodApply$(Applications.scala:434)
	dotty.tools.dotc.typer.Typer.extMethodApply(Typer.scala:148)
	dotty.tools.dotc.typer.Applications.tryApplyingExtensionMethod(Applications.scala:2687)
	dotty.tools.dotc.typer.Applications.tryApplyingExtensionMethod$(Applications.scala:434)
	dotty.tools.dotc.typer.Typer.tryApplyingExtensionMethod(Typer.scala:148)
	dotty.tools.dotc.interactive.Completion$Completer.tryApplyingReceiverToExtension$1(Completion.scala:561)
	dotty.tools.dotc.interactive.Completion$Completer.$anonfun$23(Completion.scala:604)
	scala.collection.immutable.List.flatMap(List.scala:294)
	scala.collection.immutable.List.flatMap(List.scala:79)
	dotty.tools.dotc.interactive.Completion$Completer.extensionCompletions(Completion.scala:601)
	dotty.tools.dotc.interactive.Completion$Completer.selectionCompletions(Completion.scala:449)
	dotty.tools.dotc.interactive.Completion$.computeCompletions(Completion.scala:221)
	dotty.tools.dotc.interactive.Completion$.rawCompletions(Completion.scala:80)
	dotty.tools.pc.completions.Completions.enrichedCompilerCompletions(Completions.scala:114)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:136)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:139)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: position not set for runtimeChecked(<empty>) # -1 of class dotty.tools.dotc.ast.Trees$Apply in C:/Users/seton/OneDrive/School/FINALSEM LETS GOOOOOOOO/Programming Languages/Project/vinyltracker/src/main/scala/classes/vinyls.scala