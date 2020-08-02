package advance.lectures.part4_concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Success

/**
 * This lectures shows:
 *
 * 1) how to block a thread until completion of a Future using Await.
 * 2) Promises in Scala
 */
object FuturesAndPromisesPart2 extends App {

  // online banking app
  case class User(name: String)

  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "Rock the JVM banking"

    def fetchUser(name: String): Future[User] = Future {
      // simulate fetching from the DB
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      // simulate some processes
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "SUCCESS")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      // fetch user from DB
      // create transaction
      // WAIT for the transaction to finish
      val transactionFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status


      // block a future if we need to
      Await.result(transactionFuture, 2.seconds) // blocks until all the futures are completed
      // it is used instead of Thread.sleep
    }
  }

  println(BankingApp.purchase("Daniel", "iphone12", "rock the jvm store", 3000))

  // Promises

  // manual manipulation of futures with Promises
  val promise = Promise[Int]() // "controller" over future
  val future = promise.future

  // thread 1 - "consumer"
  future.onComplete {
    case Success(r) => println(s"[consumer] I've received ${r}")
  }

  // thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)
    // "fulfilling" the promise

    // important: here we manipulate to value of the future (which is managed by the promise)
    // in order to inform that it ready and then it will be consumed by the consumer
    promise.success(42)
    println("[producer] done")
  })

  // The promise pattern allows us to separate the concern of handling (reading) a future from producing it

}
