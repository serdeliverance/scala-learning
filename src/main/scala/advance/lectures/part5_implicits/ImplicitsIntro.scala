package advance.lectures.part5_implicits

object ImplicitsIntro extends App {

  val pair = "Daniel" -> "555"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  println("Peter".greet)

  def sum(x: Int)(implicit amount: Int) = x + amount

  implicit val defaultAmount = 10

  sum(2)    // the amount is passed by the compiler by looking at the search scope.
}
