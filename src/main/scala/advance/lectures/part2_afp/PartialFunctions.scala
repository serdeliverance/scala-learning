package advance.lectures.part2_afp

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1     // Function[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNiceFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  // {1, 2, 5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function value

  println(aPartialFunction(2))
  // println(aPartialFunction(1111)) // crash with error

  // remember: a partial functions are based on pattern matching

  // PF utilities
  println(aPartialFunction.isDefinedAt(67))

  // lift
  val lifted = aPartialFunction.lift  // Int => Option[Int]
  println(lifted(2))
  println(lifted(98))

  // PF extends normal functions

  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // PF are a subtype of total functions

  // HOFs accept partial functions as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }

  /*
    Partial functions can have only ONE parameter type
   */
}
