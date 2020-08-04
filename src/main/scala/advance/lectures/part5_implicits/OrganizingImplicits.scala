package advance.lectures.part5_implicits

object OrganizingImplicits extends App {

  // implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(List(1, 4, 3, 7, 13).sorted)

  // sorted requires an implicit ordering val. It find it in scala.Predef

  /*
    Implicits (used as implicit parameters):
    - val/var
    - object
    - accessor methods = defs with no parentheses

    All of them must be defined INSIDE a class, object or a trait
   */

  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Maria", 21),
    Person("John",16)
  )

  // implicit val alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)

  // println(persons.sorted)

  /*
    Implicit scope
    - normal scope = LOCAL SCOPE      (highest priority)
    - imported scope      (example ExecutionContext.Implicits.global)
    - companion objects of all types involved in the method signature
      Example: the method
        def sorted[B >: A](implicit ord: Ordering[B]): List

        (companion object) order of search:
        - List companion
        - Ordering companion
        - all the supertype involved = A or any supertype

   */

  object AlphabeticNameOrdering {
    implicit val alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  }

  import AgeOrdering._

  println(persons.sorted)

  /*
    Exercise

    - totalPrice = most used (50%)
    - by unit count = 25%
    - by unit price = 25%
   */
  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }

  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }
}
