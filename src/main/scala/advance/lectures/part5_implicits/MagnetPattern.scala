package advance.lectures.part5_implicits

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MagnetPattern extends App {

  // method overloading

  class P2PRequest
  class P2PResponse
  class Serializer[T]

  trait actor {
    def receive(statusCode: Int): Int
    def receive(request: P2PRequest): Int
    def receive(response: P2PResponse): Int
    def receive[T: Serializer](message: T): Int
    def receive[T: Serializer](message: T, statusCode: Int): Int
    // lots of overloads
  }

  /*
    Problems
    1 - type erasure
    2 - lifting doesn't work for all overloads

        val receive = receive _     // ???

    3 - code duplication
    4 - type inferrence and default args

        actor.receive(?!)
   */

  trait MessageMagnet[Result] {
    def apply(): Result
  }

  def receive[R](magnet: MessageMagnet[R]): R = magnet()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int] {
    def apply(): Int = {
      // logic for handling P2PRequest
      println("Handling P2P request")
      42
    }
  }

  implicit class FromP2PResponse(response: P2PResponse) extends MessageMagnet[Int] {
    def apply(): Int = {
      // logic for handling P2PResponse
      println("Handling P2P response")
      24
    }
  }

  receive(new P2PRequest)
  receive(new P2PResponse)

  /*
    An implicit conversion happens above, for example: from P2PRequest to a MessageMagnet,
    then it is passed as parameter to the receive() method
    this called the magnet
    it executes its apply method
    and then the logic is executed

    So, the receive method is called... it is the core of our API (it is the magnet)
    and we used implicit conversions to specify different method overloads by keeping
    the API clean (we delegated to the compiler to inject the implicit conversion needed to
    run the overload that we wanted)
   */

  // Pros

  // 1 - no more type erasure problems
  implicit class FromResponseFuture(future: Future[P2PResponse]) extends MessageMagnet[Int] {
    override def apply(): Int = 2
  }

  implicit class FromRequestFuture(future: Future[P2PRequest]) extends MessageMagnet[Int] {
    override def apply(): Int = 3
  }

  /*
    the compiler looks for implicit conversions before type erase
   */

  println(receive(Future(new P2PRequest)))
  println(receive(Future(new P2PResponse)))

  // 2 - lifting works
  trait MathLib {
    def add1(x: Int): Int = x + 1
    def add1(s: String): Int = s.toInt + 1
    // add1 more overloads
  }

  // "magnetize"
  trait AddMagnet {
    def apply(): Int
  }

  def add1(magnet: AddMagnet): Int = magnet()

  implicit class AddInt(x: Int) extends AddMagnet {
    override def apply(): Int = x + 1
  }

  implicit class AddString(s: String) extends AddMagnet {
    override def apply(): Int = s.toInt + 1
  }

  val addFV = add1 _      // lift our magnet functions to use in HOFs
  println(addFV(1))
  println(addFV("3"))

  /*
    Drawbacks
    1 - verbose
    2 - harder to read
    3 - you can't name or place default arguments
    4 - call by name doesn't work correctly
   */

  class Handler {
    def handle(s: => String) = {
      println(s)
      println(s)
    }
    // other overloads
  }

  trait HandleMagnet {
    def apply(): Unit
  }

  def handle(magnet: HandleMagnet) = magnet()

  implicit class StringHandle(s: => String) extends HandleMagnet {
    override def apply(): Unit = {
      println(s)
      println(s)
    }
  }

  def sideEffectMethod(): String = {
    println("Hello, Scala")
    "hahaha"
  }

//  handle(sideEffectMethod())
  handle {
    println("Hello, Scala")
    "hahaha"                      // new StringHandle("magnet")
  }
}
