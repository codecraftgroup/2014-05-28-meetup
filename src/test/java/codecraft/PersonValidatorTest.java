package codecraft;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTest {
    @Test
    public void brianRussellIsAlrightWithMe() {
        // given: a name of Brian
        PersonValidator v = new PersonValidator();
        String name = "Brian";
        // when: I validate it
        List<String> validationMessages = v.validate(name);
        // then: it proves to be valid
        assertEquals(validationMessages.size(), 0);
    }

    @Test
    public void aNameShouldNeverBeginWithALowerCaseLetterOrBeEmptyOrNull() {
        //given: a list of bogus names
        PersonValidator validator = new PersonValidator();
        String[] names = {"brian", "jimmy", "harold", "", null};

        for (String name : names) {
            // when: i validate each of them
            List<String> validateMessages = validator.validate(name);

            // then: it proves to be invalid
            assertEquals(validateMessages.size(), 1);
        }
    }
}
