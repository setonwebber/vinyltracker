import scala.io.StdIn.readLine

package functions{
    def askUntilValid[T] (question: String) (validate: String => Option[T]): T = {
        var valid = false
        var result: Option[T] = None

        while (!valid) {
            val input = readLine(question)

            result = validate(input)
            valid = result.isDefined

            if (!valid) {
                println("Invalid input, try again.")
            }
        }

        result.get
    }
}