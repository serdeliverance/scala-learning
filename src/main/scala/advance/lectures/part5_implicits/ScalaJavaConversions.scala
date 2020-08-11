package advance.lectures.part5_implicits

import java.{util => ju}

/**
 * How to convert from Java classes to Scala and viceversa
 */
object ScalaJavaConversions extends App {

  import collection.JavaConverters._

  val javaSet: ju.Set[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)
  println(javaSet)

  val scalaSet = javaSet.asScala

  import collection.mutable._
  val numbersBuffer = ArrayBuffer[Int](1, 2, 3)
  val juNumbersBuffer = numbersBuffer.asJava

  println(juNumbersBuffer.asScala eq numbersBuffer)
}
