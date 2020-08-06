package advance.lectures.part5_implicits

/*
 A type class is a trait that takes a type a describes the operations to be apply to that type

 A type class defines a series of operations that can be applied to a given type.

 It describes a collection of attributes or methods that a type must have, in order to belong to
 the specific type class
 */
object TypeClasses extends App {

  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>$name ($age years old) <a href=$email></div>"
  }

  User("John", 32, "john@rockthejvm.com").toHTML

  /*
    Cons:
      1 - for the types WE write
      2 - ONE implementation out of quite a number
      (for example: we only have only have one implementation
      which applies for all User, no matters what singularity/business rule it can have)
   */

  // option 2 - pattern matching
  object HTMLSerializerPM {
    def serializeToHTML(value: Any) = value match {
      case User(n, a, e) =>
      // case java.util.Date =>
      case _ =>
    }
  }

  /*
    Cons
      1 - lost type safety
      2 - need to modify the code every time
      3 - still one implementation per type
   */

  // another approach: TYPE CLASS

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  object UserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  val john = User("John", 32, "john@rockthejvm.com")
  println(UserSerializer.serialize(john))

  // 1 - we can define serializer for other types

  import java.util.Date
  object DataSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString}</div>"
  }

  // 2 - we can define MULTIPLE serializers
  object PartialUserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = "<div></dib>"
  }

  // the template of a type class
  trait MyTypeClassTemplate[T] {
    def action(value: T): String
  }

  /**
   * Equality
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }
}
