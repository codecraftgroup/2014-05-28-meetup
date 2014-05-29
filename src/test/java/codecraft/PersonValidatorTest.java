package codecraft;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTest {

    int okAge = 54;
    int okPostalCode = 90210;

    @Test
    public void brianRussellIsAlrightWithMe() {
        // given: a name of Brian, who is 54, and lives in beverly hills
        PersonValidator v = new PersonValidator();
        String name = "Brian";
        int age = 54;
        int postalCode = 90210;

        // when: I validate it
        List<String> validationMessages = v.validate(name, age, postalCode);
        // then: it proves to be valid
        assertEquals(0, validationMessages.size());
    }

    @Test
    public void aNameShouldStartUppercased() {
        //given: a list of bogus names
        PersonValidator validator = new PersonValidator();
        class NameAndMessage {
            final String name;
            final String message;

            NameAndMessage(String name, String message) {
                this.name = name;
                this.message = message;
            }
        }
        NameAndMessage[] namesAndMessages = {
                                   new NameAndMessage("brian", "Name does not start with UPPERCASE"),
                                   new NameAndMessage("jimmy", "Name does not start with UPPERCASE"),
                                   new NameAndMessage("harold", "Name does not start with UPPERCASE"),
                                   new NameAndMessage("", "I need a name, dude."),
                                   new NameAndMessage(null, "I need a name, dude."),
                                   new NameAndMessage("7teve", "Name does not start with UPPERCASE"),
                                   new NameAndMessage("/", "Name does not start with UPPERCASE")
        };

        for (NameAndMessage nameAndMessage : namesAndMessages) {
            // when: i validate each of them
            List<String> validateMessages = validator.validate(nameAndMessage.name, okAge, okPostalCode);

            // then: it proves to be invalid
            assertEquals(1, validateMessages.size());
            assertEquals(nameAndMessage.message, validateMessages.get(0));
        }
    }

    @Test
    public void ageBetween1And100() {
        //given ages outside the valid range
        PersonValidator validator = new PersonValidator();
        class AgeAndError {
            final int age;
            final String error;

            AgeAndError(int age, String error) {
                this.age = age;
                this.error = error;
            }
        }
        AgeAndError[] ageCases = {
                                   new AgeAndError(0, "Age should be >= 1"),
                                   new AgeAndError(101, "You're too old!")};

        // when i validate them
        for (AgeAndError ageCase : ageCases) {
            List<String> validationMessages = validator.validate("Good Name", ageCase.age, okPostalCode);
            // then the prove to be invalid
            assertEquals(1, validationMessages.size());
            assertEquals(ageCase.error, validationMessages.get(0));
        }

    }

    @Test
    public void everyGoodPostalCodeHasFiveNumbers() {
        //given postalCodes that are no good
        PersonValidator validator = new PersonValidator();
        int[] badPostalCodes = {1, 22, 333, 4444, 666666, 7777777};

        // when i validate them
        for (int badPostalCode : badPostalCodes) {
            List<String> validationMessages = validator.validate("Good Name", okAge, badPostalCode);
            // then the prove to be invalid
            assertEquals(1, validationMessages.size());
            assertEquals("Postal codes must have 5 digits", validationMessages.get(0));
        }

    }
}
