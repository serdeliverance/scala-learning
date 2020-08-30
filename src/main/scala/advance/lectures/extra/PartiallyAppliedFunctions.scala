package advance.lectures.extra

object PartiallyAppliedFunctions {

  // methods are lambdas are different

  def incrementMethod(x: Int): Int = x + 1

  PartiallyAppliedFunctions.incrementMethod(3) // 4

  // you can only call a method on a instance of a class or object which encloses it

  // lambdas invocation are independent of the class, object which enclose it

  // function values can be regardless
  val incrementFunction = (x: Int) => x + 1
  val three = incrementFunction(2)

  val incrementFunctionExplicit = new Function1[Int, Int] {
    override def apply(x: Int): Int = x + 1
  }

  // eta-expansion: turn a method into a function value

  val incrementF = incrementMethod _ // eta-expansion

  // the above line tells the compiler to turn the method incrementMethod into a function

  // Thats what the compiler does:

  val incrementFExplicit = (x: Int) => incrementMethod(x)

  // The compiler can do the eta-expansion automatically if we especify the type of the function:

  val incrementF2: Int => Int = incrementMethod // eta-expansion automatically
  List(1, 2, 3).map(incrementMethod) // method auto-converted to function

  // eta-expansion on partially applied functions

  def multiArgAdder(x: Int)(y: Int) = x + y

  val add2 = multiArgAdder(2) _ // y => 2 + y           eta-expansion

  // it is useful because you can pass the rest of the arguments later in the code. Example:
  val threeAlt = add2(1)

  // other example of eta-expansion
  List(1,2,3).map(multiArgAdder(3))

  // interesting question #1
  def add(x: Int, y: Int) = x + y
  val addF = add _ // (x, y) => x + y

  // interesting quesiton #2
  def threeArgAdder(x: Int)(y: Int)(z: Int) = x + y + z
  val twoArgsRemaining = threeArgAdder(2) _ // Int => Int => Int // y => z => 2 + y + z

  val ten = twoArgsRemaining(3)(5)

  val oneArgRemaining = threeArgAdder(2)(3) _ // z => 2 + 3 + z

  // eta-expansion: takes the remaining argument list and convert it into a composed function

  // summary: eta-expansion converts a method into a Function[]
}
