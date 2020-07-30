package advance.lectures

object AdvancedPatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head")
    case _ =>
  }

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

  // infix patterns
  case class Or[A, B](a: A, b: B)
  val either = Or(2, "two")
  val humanDescription = either match {
    // case Or(number, string) => s"$number is written as $string"
    case number Or string => s"$number is written as $string" // this only works when a class has two type parameters
  }

  println(humanDescription)

  // decomposing sequences
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyList(1, 2, _*) => "starting with 1, 2"
    case _ => "something else"
  }

  // custom return types for unapply
  // isEmpty: Boolean,
}
