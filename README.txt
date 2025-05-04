The application I designed requires the Scala Build Tool (SBT).
https://www.scala-sbt.org/
This is unfortunately required to use packages, which is essential for multiple class applications like the one I have developed.

The app is also running on the 3.6.3 version of Scala. 
https://www.scala-lang.org/download/3.6.3.html 

With Scala and SBT installed, you can simply run the program with...
    > sbt run
...from the root folder. (Don't run from the src/main/scala, but from the upmost root folder.) 