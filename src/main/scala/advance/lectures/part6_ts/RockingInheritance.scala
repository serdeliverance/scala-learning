package advance.lectures.part6_ts

object RockingInheritance extends App {

  // convenience
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closeable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    // some methods
    def foreach(f: T => Unit): Unit
  }

  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.foreach(println)
    stream.close(0)
  }

  // diamond problem

  trait Animal {
    def name: String
  }

  trait Lion extends Animal {
    override def name: String = "lion"
  }

  trait Tiger extends Animal {
    override def name: String = "tiger"
  }

  class Mutant extends Lion with Tiger

  val m = new Mutant
  println(m.name)       // LAST OVERRIDE GETS PICKED

  // Last override gets picked... thats how scala compiler solves diamond problem
}
