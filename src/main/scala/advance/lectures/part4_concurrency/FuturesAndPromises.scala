package advance.lectures.part4_concurrency

import com.sun.net.httpserver.Authenticator.Success

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FuturesAndPromises extends App {

  // Futures are a functional way of computing something in parallel

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife  // calculates the meaning of life on ANOTHER THREAD
  }


  // ExecutionContext: handles thread allocation of futures

  // Future: is a computation that holds a value that is computed for somebody in some other thread
  // at some point in time

  println(aFuture.value) // Option[Try[Int]]

  println("Waiting on the future")
  aFuture.onComplete {
    case Success(meaningOfLife) => println(s"The meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with ${e}")
  }
}
