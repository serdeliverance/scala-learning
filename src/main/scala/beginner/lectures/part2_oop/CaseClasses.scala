package beginner.lectures.part2_oop

/**
 * case classes: a concise way to write classes
 *
 * It give us:
 *
 * - class parameters are fields (in other words: parameters are promoted to fields)
 * - toString, equals and hashCode implemented OOTB
 * - a copy method
 * - serialization
 * - constructor
 * - companion object already implemented
 * - CCs have extractor patterns => can be used on pattern matching
 */
object CaseClasses extends App {

  case class Person(name: String, age: Int)

  // 1. class parameters are fields
  val jim = Person("jim", 34)

  // 2. sensible toString
  println(jim)

  // 3. equals and hashcode implemented OOTB
  val jim2 = Person("jim", 34)
  println(jim == jim2)

  // 4. CCs have a handy copy method
  val jim3 = jim.copy(age = 44)
  println(jim3)

  // 5. CCs have companion objects
  val thePerson = Person
  val mary = Person("Mary", 23)     // actually, this is possible because it delegates to the apply method
                                    // of the companion objects

  // 6. CCs are serializable

  // 7. CCs have extractor patterns => can be used in PATTERN MATCHINGS
}
