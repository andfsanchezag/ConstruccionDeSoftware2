package app.adapter.in.validators;

import app.application.exceptions.InputsException;
import app.domain.model.emuns.Spices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetValidatorTest {

    private PetValidator petValidator;

    @BeforeEach
    void setUp() {
        petValidator = new PetValidator();
    }

    @Test
    void nameValidator_withValidName_shouldReturnName() throws Exception {
        String result = petValidator.nameValidator("Rex");
        assertEquals("Rex", result);
    }

    @Test
    void nameValidator_withNullName_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            petValidator.nameValidator(null)
        );
    }

    @Test
    void featuresValidator_withValidFeatures_shouldReturnFeatures() throws Exception {
        String result = petValidator.featuresValidator("Friendly");
        assertEquals("Friendly", result);
    }

    @Test
    void spicesValidator_withValidSpices_shouldReturnEnum() throws Exception {
        Spices result = petValidator.spicesValidator("DOG");
        assertEquals(Spices.DOG, result);
    }

    @Test
    void spicesValidator_withInvalidSpices_shouldThrowException() {
        assertThrows(Exception.class, () -> 
            petValidator.spicesValidator("INVALID_SPICE")
        );
    }

    @Test
    void spicesValidator_withNullSpices_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            petValidator.spicesValidator(null)
        );
    }

    @Test
    void breedValidator_withValidBreed_shouldReturnBreed() throws Exception {
        String result = petValidator.breedValidator("Labrador");
        assertEquals("Labrador", result);
    }

    @Test
    void weigthValidator_withValidWeight_shouldReturnDouble() throws Exception {
        double result = petValidator.weigthValidator("15.5");
        assertEquals(15.5, result, 0.001);
    }

    @Test
    void weigthValidator_withInvalidWeight_shouldThrowInputsException() {
        InputsException ex = assertThrows(InputsException.class, () -> 
            petValidator.weigthValidator("abc")
        );
        assertTrue(ex.getMessage().contains("peso de la mascota"));
    }

    @Test
    void ageValidator_withValidAge_shouldReturnInteger() throws Exception {
        int result = petValidator.ageValidator("5");
        assertEquals(5, result);
    }

    @Test
    void ageValidator_withInvalidAge_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            petValidator.ageValidator("not_number")
        );
    }

    @Test
    void idValidator_withValidId_shouldReturnLong() throws Exception {
        long result = petValidator.idValidator("100");
        assertEquals(100L, result);
    }

    @Test
    void idValidator_withInvalidId_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            petValidator.idValidator("xyz")
        );
    }
}
