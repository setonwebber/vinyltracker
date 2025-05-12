file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/functions/functions.scala
### java.lang.StringIndexOutOfBoundsException: Range [56, 567) out of bounds for length 561

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 56
uri: file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/vinyltracker/src/main/scala/functions/functions.scala
text:
```scala
import scala.io.StdIn.readLine

package src.main.scala..@@functions{
    def askUntilValid[Type] (question: String) (validate: String => Option[Type]): Type = {
        var valid = false
        var result: Option[Type] = None


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



#### Error stacktrace:

```
java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:55)
	java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:52)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:213)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:210)
	java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:98)
	java.base/jdk.internal.util.Preconditions.outOfBoundsCheckFromToIndex(Preconditions.java:112)
	java.base/jdk.internal.util.Preconditions.checkFromToIndex(Preconditions.java:349)
	java.base/java.lang.String.checkBoundsBeginEnd(String.java:4865)
	java.base/java.lang.String.substring(String.java:2834)
	dotty.tools.pc.completions.CompletionProvider.mkItem$1(CompletionProvider.scala:244)
	dotty.tools.pc.completions.CompletionProvider.completionItems(CompletionProvider.scala:343)
	dotty.tools.pc.completions.CompletionProvider.$anonfun$1(CompletionProvider.scala:145)
	scala.collection.immutable.List.map(List.scala:247)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:137)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: Range [56, 567) out of bounds for length 561