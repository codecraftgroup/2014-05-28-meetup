function concat(x, y) {
  return x.concat(y);
}

function comp(x, y) {
  return function(z) {
    return x(y(z));
  };
}

function get(member) {
  return function(x) {
    return x[member];
  };
}

function flatten(list) {
  return list.reduce(concat);
}

function list(x) {
  return Array.prototype.slice.call(x, 0);
}

function rest(list) {
  return Array.prototype.slice.call(list, 1);
}


function applyValidators(validators, args) {
  return flatten(
    validators.map(function(validator) {
      return validator.apply(null, args);
    })
  );
}

function Validator() {
  // [ x -> MaybeErr ] -> x -> [ Str ]
  var validators = list(arguments);
  return function() {
    return applyValidators(validators, list(arguments));
  };
}

function MaybeErr(err) {
  // [Str, Bool...] -> Maybe[Str]
  var assertions = rest(arguments),
      valid = true;

  assertions.forEach(function(bool) {
    valid = valid && bool;
  });

  return valid ? [] : [err];
}

module.exports = {
  Validator: Validator,
  MaybeErr: MaybeErr,
  get: get,
  comp: comp
}
