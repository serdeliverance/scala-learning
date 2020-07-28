package beginner.lectures.part4_patternmatching

import beginner.exercises.MyListModule._

object MorePatterns extends App {

  // just showing the most interesting samples

  val aList: MyList[Int] = Cons(1, Cons(2, Empty))

  // name binding
  val nameBinding = aList match {
    case nonEmptyList @ Cons(_, _) => // name binding... in case to use the name latter inside the case
    case Cons(1, rest @ Cons(2, _)) => // name biding in nested patterns
  }

  // multi pattern
  val multiPatterns = aList match {
    case Empty | Cons(0, _) => // compund pattern
  }

  // if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 =>
  }

}
