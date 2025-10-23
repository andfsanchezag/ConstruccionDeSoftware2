package app.adapter.in.validators;

import app.application.exceptions.InputsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleValidatorTest {

    private SimpleValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SimpleValidator() {};
    }

    @Test
    void stringValidator_withValidValue_shouldReturnValue() throws Exception {
        String result = validator.stringValidator("campo", "valor");
        assertEquals("valor", result);
    }

    @Test
    void stringValidator_withNullValue_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.stringValidator("campo", null)
        );
        assertTrue(ex.getMessage().contains("campo"));
        assertTrue(ex.getMessage().contains("vacio o nulo"));
    }

    @Test
    void stringValidator_withEmptyValue_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.stringValidator("campo", "")
        );
        assertTrue(ex.getMessage().contains("campo"));
        assertTrue(ex.getMessage().contains("vacio o nulo"));
    }

    @Test
    void integerValidator_withValidValue_shouldReturnInteger() throws Exception {
        int result = validator.integerValidator("edad", "25");
        assertEquals(25, result);
    }

    @Test
    void integerValidator_withInvalidValue_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.integerValidator("edad", "abc")
        );
        assertTrue(ex.getMessage().contains("edad"));
        assertTrue(ex.getMessage().contains("numerico"));
    }

    @Test
    void integerValidator_withNullValue_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.integerValidator("edad", null)
        );
    }

    @Test
    void longValidator_withValidValue_shouldReturnLong() throws Exception {
        long result = validator.longValidator("documento", "123456789");
        assertEquals(123456789L, result);
    }

    @Test
    void longValidator_withInvalidValue_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.longValidator("documento", "xyz")
        );
        assertTrue(ex.getMessage().contains("documento"));
        assertTrue(ex.getMessage().contains("numerico"));
    }

    @Test
    void doubleValidator_withValidValue_shouldReturnDouble() throws Exception {
        double result = validator.doubleValidator("precio", "99.99");
        assertEquals(99.99, result, 0.001);
    }

    @Test
    void doubleValidator_withInvalidValue_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.doubleValidator("precio", "invalid")
        );
        assertTrue(ex.getMessage().contains("precio"));
        assertTrue(ex.getMessage().contains("numerico"));
    }

    @Test
    void doubleValidator_withNullValue_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.doubleValidator("precio", null)
        );
    }
}
