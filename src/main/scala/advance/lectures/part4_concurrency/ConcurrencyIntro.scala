package advance.lectures.part4_concurrency

import java.util.concurrent.Executors

object ConcurrencyIntro extends App {

//  executors
    val pool = Executors.newFixedThreadPool(10)
    pool.execute(() => println("something in the thread pool"))

    pool.execute(() => {
      Thread.sleep(1000)
      println("done after 1 second")
    })

    pool.execute(() => {
      Thread.sleep(1000)
      println("almost done")
      Thread.sleep(1000)
      println("done after 2 seconds")
    })

  pool.shutdown()
}
