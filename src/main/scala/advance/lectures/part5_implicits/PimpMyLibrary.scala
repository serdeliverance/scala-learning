package advance.lectures.part5_implicits

/**
 * How to enrich existing types with implicits. This technique is known as
 * Pimp My Library
 */
object PimpMyLibrary extends App {

  // 2.isPrime

  implicit class RichInt(value: Int) {
    def isEven: Boolean = value % 2 == 0

    def sqrt: Double = Math.sqrt(value)

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] = {
        if (n <= 0) List()
        else concatenate(n - 1) ++ list
      }

      concatenate(value)
    }
  }

  42.isEven // what the compiler does is: new RichInt(42).isEven

  // type enrichment = pimping

  // another example

  1 to 10

  import scala.concurrent.duration._
  3.seconds

  /*
    Enrich the String class
    - asInt
    - encrypt
      "John" -> Lnjp

      Keep enriching the Int class
      - times(function)
        3.times(() => ...)
      - *
        3 * List(1, 2) => List(1, 2, 1, 2, 1, 2)
   */

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string) // java.lang.Integer -> Int
    def encrypt(cypherDistance: Int): String = string.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("3".asInt + 4)
  println("John".encrypt(4))

  println(3 * List(1, 2))
}
