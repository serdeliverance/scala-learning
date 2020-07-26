package lectures.part3_fpintro

object TuplesAndMaps extends App {

  // tuples - finite ordered "lists"... is a way of grouping elements
  val aTuple = (2, "hello")

  println(aTuple._1)  // 2
  println(aTuple.copy(_2 = "goodbye"))
  print(aTuple.swap) // ("hello", 2)

  // Maps - associates keys -> values
  val aMap: Map[String, Int] = Map()    // instantiating an empty map

  val phonebook = Map(("Jim", 555), ("Pepe", 665))
  val phonebook2 = Map("Jim" -> 555, "Pepe" -> 665)   // syntactic sugar

  // map operations
  println(phonebook.contains("Jim"))
  println(phonebook("Jim"))     // returns the value associated to the key "Jim"
  println(phonebook("Mary"))    // trying to retrieve the value for a inexisting key using apply method will throws an Exception

  // to avoid that, we can add a defult value...
  // val phonebook = Map(("Jim", 555), ("Pepe", 665)).withDefaultValue(-1)

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing   // it creates a new map which adds the element newPairing (maps are inmutable)

  // functions on maps
  // map, flatMap, filter

  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 444)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))
}
