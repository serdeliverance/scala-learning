package lectures.part3_fpintro

object Sequences extends App {

  // Seq
  val aSequence = Seq(1, 2, 3, 4)
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(3))
  println(aSequence ++ Seq(5, 6, 7))
  println(aSequence.sorted)

  val aRange: Seq[Int] = 1 until 10
  aRange.foreach(println)

  (1 to 10).foreach(x => println("hello"))

  // List
  val aList = List(1, 2, 3)
  val prepended = 42 :: aList       // syntatic sugar for ::(42, aList) or ::.apply(42, aList)
  println(prepended)

  val apples5 = List.fill(5)("apple")
  println(apples5)

  println(aList.mkString("|"))

  // Array
  val numbers = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[Int](3)
  threeElements.foreach(println)
}
