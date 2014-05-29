var _ = require('./lib'),
    comp = _.comp,
    get = _.get,
    MaybeErr  = _.MaybeErr,
    Validator = _.Validator;

function isNum(n) {
  return typeof n === "number";
}

function isStr(s) {
  return typeof s === "string";
}

function isCapitalized(name) {
  return MaybeErr(
      "name must be a capitalized string",
      isStr(name) &&
      name.match(/^[A-Z]/)
  );
}

function isFiveDigitNumber(zip) {
  return MaybeErr(
      "zip must be a five digit number",
      isNum(zip),
      String(zip).length == 5
  );
}

function isNumBetweenOneAndOneHundred(age) {
  return MaybeErr(
      "age must be a number between 0 and 100",
      isNum(age),
      0 < age < 100
  );
}

var validName = comp(isCapitalized, get("name")),
    validAge  = comp(isNumBetweenOneAndOneHundred, get("age")),
    validZip  = comp(isFiveDigitNumber, get("zip")),
    validate = Validator(validName, validAge, validZip),
    dataSet = require("./people.json"),
    exit = 0;

console.log("Here we go!");
dataSet.forEach(function(person) {
  var errors = validate(person),
      status = errors.length ? "ERR:" : "OK: ";

  console.log()
  console.log(status);
  console.dir(person);

  errors.forEach(function(e) {
    console.log("  " + e);
  });

  exit += errors.length;
});

process.exit(exit);
