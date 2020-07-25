package lectures.objects

object Generics extends App {

  class MyList[A] {
    // use the type A
  }

  trait MySomething[A]

  class MyMap[Key, Value]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ???
  }

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // Design choice...
  // 1. List[Cat] extends List[Animal] => COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) ????

  // 2. NO => INVARIANCE
  class InvariantList[A]

  // 3. CONTRAVARIANCE
  class ContravariantList[-A]

  // bounded types
  // allow us to use generics over a subtype of a given class, or a super class
  class Cage[A <: Animal](animal: A)   // class Cage only accepts subtypes of class Animal (upper type bound)

  val cage = new Cage(new Dog)

  // class Cage[A >: Animal]      low type bound
}
