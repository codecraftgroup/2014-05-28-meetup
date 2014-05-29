package codecraft;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTest {
    @Test
    public void brianRussellIsAlrightWithMe() {
        // given: a name of Brian, who is 54
        PersonValidator v = new PersonValidator();
        String name = "Brian";
        int age = 54;
        // when: I validate it
        List<String> validationMessages = v.validate(name, age);
        // then: it proves to be valid
        assertEquals(0, validationMessages.size());
    }

    @Test
    public void aNameShouldStartUppercased() {
        //given: a list of bogus names
        PersonValidator validator = new PersonValidator();
        String[] names = {"brian", "jimmy", "harold", "", null, "7teve", "/"};

        for (String name : names) {
            // when: i validate each of them
            int okAge = 54;
            List<String> validateMessages = validator.validate(name, okAge);

            // then: it proves to be invalid
            assertEquals(1, validateMessages.size());
        }
    }

    @Test
    public void ageBetween1And100() {
    	//given ages outside the valid range
        PersonValidator validator = new PersonValidator();
        int[] ages = {0, 101};

        // when i validate them
        for (int age : ages) {
            List<String> validationMessages = validator.validate("Good Name", age);
            // then the prove to be invalid
            assertEquals(1, validationMessages.size());
        }

    }
}
