package advance.lectures.part1_intro

import scala.util.Try

object DarkSugars extends App {

  // syntax sugar #1: methods with single param
  def singleArgumentMethod(arg: Int): String = s"$arg little ducks.."

  val description = singleArgumentMethod {
    // write some code
    2
  }

  // another example
  val aTryInstance = Try {
    throw new RuntimeException
  }

  List(1, 2, 3).map { x =>
    x + 1
  }

  // syntax sugar #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  // similar to functional interfaces in Java... I can extend the trait or implement its method anonymous
  val anInstance: Action = x => x * 10

  // syntax sugar #3: the :: and #:: methods are special

  val prependedList = 2 :: List(3, 4)
  // 2.::(List(3,4))
  // List(3,4).::(2)
  // ?!

  // scala spec: last character decides associativity of methods
  // methods ending with ':' are right-associative
  1 :: 2 :: 3 :: List(4, 5)
  List(4, 5).::(3).::(2).::(1) // its the same

  // syntax sugar #4: multi-word method naming

  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala in so sweet"

  // syntax sugar #5: infix types
  class Composite[A, B]

  val composite: Int Composite String = ???

  class -->[A, B]

  val towards: Int --> String = ???

  // syntax sugar #6: update() is very special, much like apply
  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7)
  // used in mutable collections

  // syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation

    def member = internalMember // "getter"
    def member_=(value: Int): Unit =
      internalMember = value // "setter"
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewritten as aMutableContainer.member_=(42)
}
