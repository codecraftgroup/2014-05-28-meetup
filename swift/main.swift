
class Invalidation <T> {

  let that: T, errors: [String]

  init (_ t: T, _ e: [String] = []) { (that, errors) = (t, e) }

  subscript (ƒinvalid: T -> String?) -> Invalidation {

    if let error = ƒinvalid(that) { return Invalidation(that, [error] + errors) }

      return self
  }
}

extension Invalidation: Printable {

  var description: String { return "Invalidation(\(that), \(errors))" }
}

operator infix <*> { associativity left } // for Hal

func <*> <T> (lhs: Invalidation<T>, rhs: T -> String?) -> Invalidation<T> { return lhs[rhs] }


class Person: Printable {

  let nom: String, zip: Int, age: Int

  init (_ n: String, _ z: Int, _ a: Int) { (nom, zip, age) = (n, z, a) }

  var description: String { return "Person(\(nom), \(zip), \(age))" }
}

let people = [

  Person("Brian", 90265, 54), Person("brian", 123456, 105),

  Person("Roe", 90100, 5), Person("Joe", 0, 22), Person("", -1, -20), Person("Joe", 99999, 50),
]


import Foundation

func isFirstUppercase ($: String) -> Bool { // there MUST be a better way!

  for ch in $ { for up in $.uppercaseString { return ch == up } }; return false
}

for person in people { println(

  Invalidation(person)

    <*> { let age = $0.age; return !(0 < age && age <= 100) ? "!age" : nil }

    <*> { let zip = $0.zip; return zip <= 0 || zip > 99999 ? "!zip" : nil }

    <*> { let nom = $0.nom; return !isFirstUppercase(nom) ? "!cap" : nil }
  )
}

/*
Invalidation(Person(Brian, 90265, 54), [])
Invalidation(Person(brian, 123456, 105), [!cap, !zip, !age])
Invalidation(Person(Roe, 90100, 5), [])
Invalidation(Person(Joe, 0, 22), [!zip])
Invalidation(Person(, -1, -20), [!cap, !zip, !age])
Invalidation(Person(Joe, 99999, 50), [])
*/
