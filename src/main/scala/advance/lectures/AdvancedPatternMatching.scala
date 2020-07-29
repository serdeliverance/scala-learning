package advance.lectures

object AdvancedPatternMatching extends App {

  // this lecture explains how to create your own pattern matching compatible structure
  class Person(val name: String, val age: Int)

  object Person {
    // unapply method does the trick
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None
      else Some(person.name, person.age)

    // you can define multiple on apply if you wish (it means multiples matches to evaluate)
    def unapply(age: Int): Option[String] =
      Some(if (age < 21) "minor" else "mayor")
  }

  val bob = new Person("bob", 25)
  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I'm $a years old"
  }

  println(greeting)

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus)
}
