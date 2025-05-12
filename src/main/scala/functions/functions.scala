import scala.io.StdIn.readLine

package vinyltracker.functions{
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

