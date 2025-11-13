package app.adapter.in.builder;

import app.adapter.in.validators.ClinicalOrderValidator;
import app.adapter.in.validators.PetValidator;
import app.adapter.in.validators.UserValidator;
import app.application.exceptions.InputsException;
import app.domain.model.ClinicalOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class ClinicalOrderBuilderTest {

    @Mock
    private UserValidator userValidator;

    @Mock
    private PetValidator petValidator;

    @Mock
    private ClinicalOrderValidator clinicalOrderValidator;

    @InjectMocks
    private ClinicalOrderBuilder clinicalOrderBuilder;

    @BeforeEach
    void setUp() {
        try {
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("documento invalido");
                return Long.parseLong(s);
            }).when(userValidator).documentValidator(anyString());
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("id invalido");
                return Long.parseLong(s);
            }).when(petValidator).idValidator(anyString());
            when(clinicalOrderValidator.medicineValidator(anyString())).thenAnswer(i -> i.getArgument(0));
            when(clinicalOrderValidator.doceValidator(anyString())).thenAnswer(i -> i.getArgument(0));
        } catch (Exception e) {
            fail("Setup failed");
        }
    }

    @Test
    void builder_withValidData_shouldReturnClinicalOrder() throws Exception {
        // Arrange
        String document = "987654321";
        String petId = "100";
        String medicine = "Antibiotic";
        String doce = "500mg";

        // Act
        ClinicalOrder result = clinicalOrderBuilder.builder(document, petId, medicine, doce);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getVeterinarian());
        assertEquals(987654321L, result.getVeterinarian().getDocument());
        assertNotNull(result.getPet());
        assertEquals(100L, result.getPet().getId());
        assertEquals("Antibiotic", result.getMedicine());
        assertEquals("500mg", result.getDoce());

        verify(userValidator).documentValidator(document);
        verify(petValidator).idValidator(petId);
        verify(clinicalOrderValidator).medicineValidator(medicine);
        verify(clinicalOrderValidator).doceValidator(doce);
    }

    @Test
    void builder_withInvalidDocument_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("documento invalido")).when(userValidator).documentValidator("invalid");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalOrderBuilder.builder("invalid", "100", "medicine", "dose")
        );
    }

    @Test
    void builder_withInvalidPetId_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("id invalido")).when(petValidator).idValidator("invalid");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalOrderBuilder.builder("123", "invalid", "medicine", "dose")
        );
    }

    @Test
    void builder_withNullMedicine_shouldThrowInputsException() throws Exception {
        // Arrange
        when(clinicalOrderValidator.medicineValidator(null)).thenThrow(new InputsException("medicina nula"));

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalOrderBuilder.builder("123", "100", null, "dose")
        );
    }

    @Test
    void builder_withEmptyDoce_shouldThrowInputsException() throws Exception {
        // Arrange
        when(clinicalOrderValidator.doceValidator("")).thenThrow(new InputsException("dosis vacia"));

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalOrderBuilder.builder("123", "100", "medicine", "")
        );
    }

    @Test
    void builder_multipleCalls_shouldCreateMultipleOrders() throws Exception {
        // Act
        ClinicalOrder order1 = clinicalOrderBuilder.builder("111", "1", "Med1", "Dose1");
        ClinicalOrder order2 = clinicalOrderBuilder.builder("222", "2", "Med2", "Dose2");

        // Assert
        assertNotEquals(order1, order2);
        assertEquals(111L, order1.getVeterinarian().getDocument());
        assertEquals(222L, order2.getVeterinarian().getDocument());
        assertEquals(1L, order1.getPet().getId());
        assertEquals(2L, order2.getPet().getId());
    }
}
