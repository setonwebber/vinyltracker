error id: `<none>`.
file:///C:/Users/seton/OneDrive/School/FINALSEM%20LETS%20GOOOOOOOO/Programming%20Languages/Project/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import java.net.InetSocketAddress

object Main extends App {
  // Create an HTTP server on port 8080
  val server = HttpServer.create(new InetSocketAddress(8080), 0)

  // Define a handler for the root path ("/")
  server.createContext("/", new HttpHandler {
    def handle(exchange: HttpExchange): Unit = {
      val response = "Hello, Scala 3 Web App!"
      exchange.sendResponseHeaders(200, response.length)
      val os = exchange.getResponseBody
      os.write(response.getBytes)
      os.close()
    }
  })

  // Start the server
  server.start()
  println("Server started on http://localhost:8080")
}
```

#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.