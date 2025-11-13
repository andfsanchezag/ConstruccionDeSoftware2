package app.adapter.in.validators;

import app.application.exceptions.InputsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    void nameValidator_withValidName_shouldReturnName() throws Exception {
        String result = userValidator.nameValidator("Juan Perez");
        assertEquals("Juan Perez", result);
    }

    @Test
    void nameValidator_withNullName_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            userValidator.nameValidator(null)
        );
        assertTrue(ex.getMessage().contains("nombre de la persona"));
    }

    @Test
    void userNameValidator_withValidUsername_shouldReturnUsername() throws Exception {
        String result = userValidator.userNameValidator("admin123");
        assertEquals("admin123", result);
    }

    @Test
    void userNameValidator_withEmptyUsername_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            userValidator.userNameValidator("")
        );
    }

    @Test
    void passwordValidator_withValidPassword_shouldReturnPassword() throws Exception {
        String result = userValidator.passwordValidator("pass1234");
        assertEquals("pass1234", result);
    }

    @Test
    void passwordValidator_withNullPassword_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            userValidator.passwordValidator(null)
        );
    }

    @Test
    void documentValidator_withValidDocument_shouldReturnLong() throws Exception {
        long result = userValidator.documentValidator("123456789");
        assertEquals(123456789L, result);
    }

    @Test
    void documentValidator_withInvalidDocument_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            userValidator.documentValidator("abc")
        );
        assertTrue(ex.getMessage().contains("documento de la persona"));
    }

    @Test
    void ageValidator_withValidAge_shouldReturnInteger() throws Exception {
        int result = userValidator.ageValidator("30");
        assertEquals(30, result);
    }

    @Test
    void ageValidator_withInvalidAge_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            userValidator.ageValidator("not_a_number")
        );
        assertTrue(ex.getMessage().contains("edad de la persona"));
    }

    @Test
    void ageValidator_withNegativeAge_shouldReturnNegative() throws Exception {
        int result = userValidator.ageValidator("-5");
        assertEquals(-5, result);
    }
}
