package app.adapter.in.validators;

import app.application.exceptions.InputsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClinicalRecordValidatorTest {

    private ClinicalRecordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ClinicalRecordValidator();
    }

    @Test
    void motiveValidator_withValidMotive_shouldReturnMotive() throws Exception {
        String result = validator.motiveValidator("Consulta general");
        assertEquals("Consulta general", result);
    }

    @Test
    void motiveValidator_withNullMotive_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.motiveValidator(null)
        );
    }

    @Test
    void diagnosisValidator_withValidDiagnosis_shouldReturnDiagnosis() throws Exception {
        String result = validator.diagnosisValidator("Gripe");
        assertEquals("Gripe", result);
    }

    @Test
    void diagnosisValidator_withEmptyDiagnosis_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.diagnosisValidator("")
        );
    }

    @Test
    void medicineValidator_withValidMedicine_shouldReturnMedicine() throws Exception {
        String result = validator.medicineValidator("Antibiotico");
        assertEquals("Antibiotico", result);
    }

    @Test
    void doceValidator_withValidDose_shouldReturnDose() throws Exception {
        String result = validator.doceValidator("100mg");
        assertEquals("100mg", result);
    }

    @Test
    void procedureValidator_withValidProcedure_shouldReturnProcedure() throws Exception {
        String result = validator.procedureValidator("Cirugia");
        assertEquals("Cirugia", result);
    }

    @Test
    void procedureDetailsValidator_withValidDetails_shouldReturnDetails() throws Exception {
        String result = validator.procedureDetailsValidator("Detalles del procedimiento");
        assertEquals("Detalles del procedimiento", result);
    }

    @Test
    void allergiesValidator_withValidAllergies_shouldReturnAllergies() throws Exception {
        String result = validator.allergiesValidator("Polen");
        assertEquals("Polen", result);
    }

    @Test
    void vaccinationValidator_withValidVaccination_shouldReturnVaccination() throws Exception {
        String result = validator.vaccinationValidator("Rabia");
        assertEquals("Rabia", result);
    }

    @Test
    void symptomsValidator_withValidSymptoms_shouldReturnSymptoms() throws Exception {
        String result = validator.symptomsValidator("Fiebre, tos");
        assertEquals("Fiebre, tos", result);
    }

    @Test
    void symptomsValidator_withNullSymptoms_shouldThrowInputsException() {
        assertThrows(InputsException.class, () -> 
            validator.symptomsValidator(null)
        );
    }
}
