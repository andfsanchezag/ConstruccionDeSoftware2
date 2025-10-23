package app.adapter.in.validators;

import app.application.exceptions.InputsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClinicalOrderValidatorTest {

    private ClinicalOrderValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ClinicalOrderValidator();
    }

    @Test
    void medicineValidator_withValidMedicine_shouldReturnMedicine() throws Exception {
        String result = validator.medicineValidator("Paracetamol");
        assertEquals("Paracetamol", result);
    }

    @Test
    void medicineValidator_withNullMedicine_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.medicineValidator(null)
        );
        assertTrue(ex.getMessage().contains("medicina de la orden"));
    }

    @Test
    void doceValidator_withValidDose_shouldReturnDose() throws Exception {
        String result = validator.doceValidator("500mg");
        assertEquals("500mg", result);
    }

    @Test
    void doceValidator_withEmptyDose_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.doceValidator("")
        );
    }

    @Test
    void idValidator_withValidId_shouldReturnLong() throws Exception {
        long result = validator.idValidator("123");
        assertEquals(123L, result);
    }

    @Test
    void idValidator_withInvalidId_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.idValidator("invalid")
        );
        assertTrue(ex.getMessage().contains("id de la orden"));
    }

    @Test
    void idValidator_withNullId_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.idValidator(null)
        );
    }
}
