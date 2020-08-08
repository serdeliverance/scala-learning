package advance.lectures.part5_implicits

import advance.lectures.part5_implicits.TypeClasses.{HTMLSerializer, PartialUserSerializer}

object TypeClassesPart2 extends App {

  // Part 2 - the power of implicits on type classes
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    // a better design can also have this method
    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  // without taking advantage of the implicit parameter in our method

  //  object IntSerializer extends HTMLSerializer[Int] {
  //    override def serialize(value: Int): String = s"<div style: color=blue>$value</div>"
  //  }
  //
  //  println(HTMLSerializer.serialize(42)(IntSerializer))

  // look at the implicit... we can take advantage of implicits

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style: color=blue>$value</div>"
  }

  // this has the advantage that we can serialize any kind of value T as long as
  // we provide some implicit serializer of type T that the compiler can use.

  println(HTMLSerializer.serialize(42))

  case class User(name: String, age: Int, email: String)

  val john = User("john", 23, "john@doe.com")

  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name} (${user.age} years old) <a href=${user.email}div>"
  }

  object PartialUserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = "<div></dib>"
  }

  // this has the advantage that we can access all the entire type class interface
  println(HTMLSerializer[User].serialize(john))

  // reviewing the template...
  // TYPE CLASS

  trait MyTypeClassTemplate[T] {
    def action(value: T): String
  }

  object MyTypeClassTemplate {
    def apply[T](implicit instance: MyTypeClassTemplate[T]) = instance
  }

  /**
   * Exercise: implement the type class pattern for the equality TC.
   */

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  val anotherJohn = User("anotherjohn", 23, "anotherjohn@doe.com")
  println(Equal.apply(john, anotherJohn))

  // it is known as AD-HOC polymorphism
  // because the compiler, looking at the types of Equals.apply(elemA, elemB) can infer
  // the corret type class instance to use with those types

  // Part 3

  // using implicits
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHTML)  // println(new HTMLEnrichment[User](john).toHTML(UserSerializer)
  // COOL!

  /*
    - extend functionality to new types
    - different implementations for the same type => choose implementation
   */

  println(2.toHTML) // extend functionality to new types
  println(john.toHTML(PartialUserSerializer)) // choose implementation

  /*
    Type Class pattern parts:

    - type class itself                                     --- HTMLSerializer[T] { .. }
    - type class instances (some of which are implicit)     --- UserSerializer, IntSerializer
    - conversion with implicit classes)                     --- HTMLEnrichment
   */

  // context bounds
  def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body>${content.toHTML(serializer)}</body></html>"

  def htmlSugar[T : HTMLSerializer](content: T): String =   // it is the same as htmlBoilerplate
    s"<html><body>${content.toHTML}</body></html>"

  // [T : HTMLSerializer] is a context bound which tells the compiler to inject an
  // HTMLSerializer[T]


  // implicitly
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the code...
  val standardPerms = implicitly[Permissions]
}