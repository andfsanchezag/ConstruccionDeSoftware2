package app.adapter.in.builder;

import app.adapter.in.validators.ClinicalOrderValidator;
import app.adapter.in.validators.ClinicalRecordValidator;
import app.adapter.in.validators.PetValidator;
import app.adapter.in.validators.UserValidator;
import app.application.exceptions.InputsException;
import app.domain.model.ClinicalRecord;
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
class ClinicalRecordBuilderTest {

    @Mock
    private ClinicalRecordValidator clinicalRecordValidator;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PetValidator petValidator;

    @Mock
    private ClinicalOrderValidator clinicalOrderValidator;

    @InjectMocks
    private ClinicalRecordBuilder clinicalRecordBuilder;

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
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("id mascota invalido");
                return Long.parseLong(s);
            }).when(petValidator).idValidator(anyString());
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("id orden invalido");
                return Long.parseLong(s);
            }).when(clinicalOrderValidator).idValidator(anyString());
        } catch (Exception e) {
            fail("Setup failed");
        }
    }

    @Test
    void create_withValidData_shouldReturnClinicalRecord() throws Exception {
        // Arrange
        String document = "11111111";
        String petId = "50";
        String orderId = "200";

        // Act
        ClinicalRecord result = clinicalRecordBuilder.create(document, petId, orderId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getVeterinarian());
        assertEquals(11111111L, result.getVeterinarian().getDocument());
        assertNotNull(result.getPet());
        assertEquals(50L, result.getPet().getId());
        assertNotNull(result.getClinicalOrder());
        assertEquals(200L, result.getClinicalOrder().getId());
        assertNotNull(result.getDate());
        assertTrue(result.isStatus());

        verify(userValidator).documentValidator(document);
        verify(petValidator).idValidator(petId);
        verify(clinicalOrderValidator).idValidator(orderId);
    }

    @Test
    void create_withInvalidDocument_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("documento invalido")).when(userValidator).documentValidator("invalid");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalRecordBuilder.create("invalid", "50", "200")
        );
    }

    @Test
    void create_withInvalidPetId_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("id mascota invalido")).when(petValidator).idValidator("abc");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalRecordBuilder.create("111", "abc", "200")
        );
    }

    @Test
    void create_withInvalidOrderId_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("id orden invalido")).when(clinicalOrderValidator).idValidator("xyz");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalRecordBuilder.create("111", "50", "xyz")
        );
    }

    @Test
    void create_withNullDocument_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("documento nulo")).when(userValidator).documentValidator((String) null);

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            clinicalRecordBuilder.create(null, "50", "200")
        );
    }

    @Test
    void create_multipleCalls_shouldCreateMultipleRecords() throws Exception {
        // Act
        ClinicalRecord record1 = clinicalRecordBuilder.create("100", "1", "10");
        ClinicalRecord record2 = clinicalRecordBuilder.create("200", "2", "20");

        // Assert
        assertNotEquals(record1, record2);
        assertEquals(100L, record1.getVeterinarian().getDocument());
        assertEquals(200L, record2.getVeterinarian().getDocument());
        assertTrue(record1.isStatus());
        assertTrue(record2.isStatus());
    }

    @Test
    void create_shouldSetStatusToTrue() throws Exception {
        // Act
        ClinicalRecord result = clinicalRecordBuilder.create("123", "456", "789");

        // Assert
        assertTrue(result.isStatus());
    }

    @Test
    void create_shouldSetCurrentDate() throws Exception {
        // Act
        ClinicalRecord result = clinicalRecordBuilder.create("123", "456", "789");

        // Assert
        assertNotNull(result.getDate());
    }
}
