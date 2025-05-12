error id: scala/None.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/functions/functions.scala
empty definition using pc, found symbol in pc: scala/None.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -None.
	 -None#
	 -None().
	 -scala/Predef.None.
	 -scala/Predef.None#
	 -scala/Predef.None().
offset: 204
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/functions/functions.scala
text:
```scala
import scala.io.StdIn.readLine

package functions{
    def askUntilValid[Type] (question: String) (validate: String => Option[Type]): Type = {
        var valid = false
        var result: Option[T] = Non@@e


        while (!valid) {
            val input = readLine(question).trim

            result = validate(input)
            valid = result.isDefined

            if (!valid) {
                println("Invalid input, try again.")
            }
        }

        // when valid is true, return result as value.
        result.get
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/None.