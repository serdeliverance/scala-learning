package advance.lectures.part4_concurrency

import scala.Console.println

object ConcurrencyProblems extends App {

  // concurrency problems

  /*
    The following is a scenario when race condition happend and the result
    of the computation is not deterministic
   */
  def runInParallel = {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x)
  }

  // for (_ <- 1 to 10000) runInParallel   // we have race conditions

  class BankAccount(var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
    println("I've bought " + thing)
    println("my account is now " + account)
  }

  /*
    The following code shows how different runs of concurrent code can lead to different results: Race conditions
   */
  for (_ <- 1 to 10000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "iphone12", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(100)
    if (account.amount != 43000) println("AHA: " + account.amount)
  }

  /*
    Explanation of the Race Condition:

    The computation starts and two threads are run:

    thread1 (shoes): 50000    // the amount that this thread sees
    thread2 (iphone): 50000

    account.amount =- price   is not an atomic operation

    account = account - price

    So... is the following happens at the same time:

    Thread1                           | Thread2
    - account = 50000 - 3000 = 47000  | - account = 50000 - 4000 = 46000

    So.. the last thread (thread2) ends overriding the shared memory. which in these case is account
   */

  // Solutions:

  // option #1: use synchronized()

  // synchronize the critical are that you want to be thread safe
  def buySafe(account: BankAccount, thing: String, price: Int) =
    account.synchronized{
      // no two threads can evaluate this at the same time
      account.amount -= price
      println("I've bought " + thing)
      println("my account is now" + account)
    }

  // option #2: use @volatile

  /*
  def buy(@volatile account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
    println("I've bought " + thing)
    println("my account is now " + account)
  }
  */


  /**
   * Exercises
   *
   * 1) Construct 50 "inceptions" threads
   *    Thread1 -> Thread2 -> Thread3
   *    println("hello from thread #3")
   *
   *    in REVERSE ORDER
   */
  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread ${i}")
  })

  inceptionThreads(50).start()
  /**
   * 2)
   *  a) what is the biggest value possible for x? 100
   *  b) what is the SMALLEST value possible for x? 1
   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))

  /**
   * 3) Sleep fallacy
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  /*
    what's the value of message? almost always "Scala is awesome"
    is it guaranteed? No
    why? why not?

    An execution sample:

    (main thread)
      message = "Scala sucks"
      awesomeThread.start()(
      sleep() - relieves execution

      (awesome thread)
        sleep() - relieves execution

      (OS gives the CPU to some important thread - takes CPU for more than 2 seconds)
      (OS gives the CPU back to the main thread)
        print("Scala sucks2")
       (OS gives the CPU to awesome thread)
          message = "Scala is awesome"

    Explanation: making a thread sleep for 1 second and then, making the following thread sleep for 2 seconds does
    not guarantees execution order because the underlying OS can take another process which is out of our control
    and put it into execution. Imagine that this new process is CPU bound and takes a computation that is longer than
    2 seconds after finished. So, when it finished, more than 2 seconds has passed and now our two threads are ready
    to run, and we can not determine their execution order.
   */
}
