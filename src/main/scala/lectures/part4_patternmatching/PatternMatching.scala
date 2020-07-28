package lectures.part4_patternmatching

import scala.util.Random

class PatternMatching extends App {

  // switch on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // _ => WILDCARD
  }

  println(x)
  println(description)

  // 1. Descompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }

  // Pattern matching on seal hierarchies
  sealed trait Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greerting: String) extends Animal

  val animal: Animal = Dog("Terra nova")

  animal match {
    case Dog(someBreed) => println("matching a dog")
    case _ => println("I match another animal")
  }
}
