package app.adapter.in.validators;

import app.application.exceptions.InputsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceValidatorTest {

    private InvoiceValidator validator;

    @BeforeEach
    void setUp() {
        validator = new InvoiceValidator();
    }

    @Test
    void productNameValidator_withValidName_shouldReturnName() throws Exception {
        String result = validator.productNameValidator("Medicamento X");
        assertEquals("Medicamento X", result);
    }

    @Test
    void productNameValidator_withNullName_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.productNameValidator(null)
        );
        assertTrue(ex.getMessage().contains("nombre del producto"));
    }

    @Test
    void petIdValidator_withValidId_shouldReturnLong() throws Exception {
        long result = validator.petIdValidator("456");
        assertEquals(456L, result);
    }

    @Test
    void petIdValidator_withInvalidId_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.petIdValidator("abc")
        );
        assertTrue(ex.getMessage().contains("id de la mascota"));
    }

    @Test
    void ownerDocumentValidator_withValidDocument_shouldReturnLong() throws Exception {
        long result = validator.ownerDocumentValidator("123456789");
        assertEquals(123456789L, result);
    }

    @Test
    void ownerDocumentValidator_withInvalidDocument_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.ownerDocumentValidator("invalid")
        );
    }

    @Test
    void isMedicineValidator_withSi_shouldReturnTrue() throws Exception {
        boolean result = validator.isMedicineValidator("si");
        assertTrue(result);
    }

    @Test
    void isMedicineValidator_withNo_shouldReturnFalse() throws Exception {
        boolean result = validator.isMedicineValidator("no");
        assertFalse(result);
    }

    @Test
    void isMedicineValidator_withNullValue_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.isMedicineValidator(null)
        );
    }

    @Test
    void orderIdValidator_withValidId_shouldReturnLong() throws Exception {
        long result = validator.orderIdValidator("789");
        assertEquals(789L, result);
    }

    @Test
    void orderIdValidator_withInvalidId_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.orderIdValidator("xyz")
        );
        assertTrue(ex.getMessage().contains("id de la orden"));
    }

    @Test
    void amountValidator_withValidAmount_shouldReturnDouble() throws Exception {
        double result = validator.amountValidator("100.50");
        assertEquals(100.50, result, 0.001);
    }

    @Test
    void amountValidator_withInvalidAmount_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            validator.amountValidator("invalid")
        );
        assertTrue(ex.getMessage().contains("precio de la factura"));
    }
}
