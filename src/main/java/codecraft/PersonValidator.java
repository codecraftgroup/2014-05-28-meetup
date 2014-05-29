package codecraft;

import java.util.ArrayList;
import java.util.List;

public class PersonValidator {
    public List<String> validate(String name, int age) {
        // name should not start with a lowercase character
        ArrayList<String> messages = new ArrayList<String>();

        if (name == null || name.length() == 0) {
            messages.add("I need a name, dude.");
        } else {
            char c = name.charAt(0);
            if (!Character.isUpperCase(c)) {
                messages.add("Name does not start with UPPERCASE");
            }
        }

        if (age < 1) {
            messages.add("Age should be >= 1");
        } else if (age > 100) {
            messages.add("You're too old!");
        }

        return messages;
    }
}
