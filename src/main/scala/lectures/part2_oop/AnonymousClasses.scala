package lectures.part2_oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("aaaaaaaaaaaaaa")
  }

  /*
    Equivalent with

    class AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("aaaaaaaaaaaaaa")
    }

    val funnyAnimal: Animal = new AnonymousClasses$$anon$1


    This only work for traits and classes (abstracts or not)

    Rules:

      - pass in required constructor arguments if needed
      - implement all abstract fieds/methods
   */

  println(funnyAnimal.getClass)
}
