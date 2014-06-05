
class Person: Printable {
  
  let nom: String, zip: Int, age: Int
  
  init(_ n: String, _ z: Int, _ a: Int) { nom = n; zip = z; age = a }
  
  var description: String { return "Person(\(nom), \(zip), \(age))" }
}

class Invalidation: Printable {
  
  let person: Person, errors: String[]
  
  init(_ p: Person, _ e: String[] = []) { person = p; errors = e }
  
  subscript(invalid: Person -> String?) -> Invalidation {
    
    if let error = invalid(person) { return Invalidation(person, [error] + errors) }
      
      return self
  }
  
  var description: String { return "Invalidation(\(person), \(errors))" }
}

operator infix <*> { associativity left } // for Hal

func <*> (this: Invalidation, invalid: Person -> String?) -> Invalidation { return this[invalid] }

let people = [
  
  Person("Brian", 90265, 54), Person("brian", 123456, 105),

  Person("Roe", 90100, 5), Person("Joe", 0, 22), Person("", -1, -20), Person("Joe", 99999, 50),
]

for person in people {
  
  func isFirstUppercase(s: String) -> Bool { // there MUST be a better way!
    
    for ch in s { for up in s.uppercaseString { return ch == up } }; return false
  }

  println(Invalidation(person)
    
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
