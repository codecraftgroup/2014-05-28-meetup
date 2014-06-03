
package codecraft

/**
 * @author 601
 * @version 2014.06.01
 */

case class Invalidation[T](that: T, errors: List[String] = Nil) {

  /**
   *  @param iƒ partially returns an error
   */
  def apply(iƒ: PartialFunction[T, String]) = (iƒ lift that fold this) { error => copy(errors = error :: errors) }
}

case class Person(name: String, zipCode: Int, age: Int)

object Validator extends App {

  val people = List(

    Person("Brian", 90265, 54), Person("brian", 902657, 105),

    Person("Roe", 90100, 5), Person("Joe", 0, 22), Person(null, -1, -20), Person("Joe", 99999, 50)
  )

  for (person <- people) println(

    Invalidation(person) // match for errors

      { case Person(_, _, age) if !(0 < age && age <= 100) => "! 0 < age <= 100" }

      { case Person(_, zip, _) if zip <= 0 || zip > 99999 => "! exactly 5 digits" }

      { case Person(nom, _, _) if nom == null || nom.isEmpty || nom.head.isLower => "! capitalized" }
  )
}

/*
Invalidation(Person(Brian,90265,54),List())
Invalidation(Person(brian,902657,105),List(! capitalized, ! exactly 5 digits, ! 0 < age <= 100))
Invalidation(Person(Roe,90100,5),List())
Invalidation(Person(Joe,0,22),List(! exactly 5 digits))
Invalidation(Person(null,-1,-20),List(! capitalized, ! exactly 5 digits, ! 0 < age <= 100))
Invalidation(Person(Joe,99999,50),List())
*/
