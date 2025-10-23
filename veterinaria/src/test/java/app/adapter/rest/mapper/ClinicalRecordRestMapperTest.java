package app.adapter.rest.mapper;

import app.adapter.in.builder.ClinicalRecordBuilder;
import app.adapter.in.validators.ClinicalRecordValidator;
import app.adapter.rest.request.ClinicalRecordRequest;
import app.adapter.rest.response.ClinicalRecordResponse;
import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClinicalRecordRestMapperTest {

    @Mock
    private ClinicalRecordBuilder clinicalRecordBuilder;

    @Mock
    private ClinicalRecordValidator clinicalRecordValidator;

    @InjectMocks
    private ClinicalRecordRestMapper clinicalRecordRestMapper;

    private ClinicalRecordRequest request;
    private ClinicalRecord record;
    private Pet pet;
    private User veterinarian;
    private ClinicalOrder order;

    @BeforeEach
    void setUp() {
        request = new ClinicalRecordRequest();
        request.setOrderId("50");
        request.setPetId("10");
        request.setVeterinarianDocument("222222");
        request.setMotive("Regular checkup");
        request.setDiagnosis("Healthy");
        request.setMedicine("Vaccine");
        request.setDoce("1ml");

        pet = new Pet();
        pet.setId(10L);

        veterinarian = new User();
        veterinarian.setDocument(222222L);

        order = new ClinicalOrder();
        order.setId(50L);

        record = new ClinicalRecord();
        record.setId(100L);
        record.setPet(pet);
        record.setVeterinarian(veterinarian);
        record.setClinicalOrder(order);
        record.setDate(new Date(System.currentTimeMillis()));
        record.setStatus(true);
        record.setMotive("Regular checkup");
        record.setDiagnosis("Healthy");
        record.setMedicine("Vaccine");
        record.setDoce("1ml");
    }

    @Test
    void toDomain_shouldCallBuilderAndValidators() throws Exception {
        // Arrange
        when(clinicalRecordBuilder.create(anyString(), anyString(), anyString()))
            .thenReturn(record);
        when(clinicalRecordValidator.motiveValidator("Regular checkup")).thenReturn("Regular checkup");
        when(clinicalRecordValidator.diagnosisValidator("Healthy")).thenReturn("Healthy");
        when(clinicalRecordValidator.medicineValidator("Vaccine")).thenReturn("Vaccine");
        when(clinicalRecordValidator.doceValidator("1ml")).thenReturn("1ml");

        // Act
        ClinicalRecord result = clinicalRecordRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        verify(clinicalRecordBuilder).create("222222", "10", "50");
        verify(clinicalRecordValidator).motiveValidator("Regular checkup");
        verify(clinicalRecordValidator).diagnosisValidator("Healthy");
        verify(clinicalRecordValidator).medicineValidator("Vaccine");
        verify(clinicalRecordValidator).doceValidator("1ml");
        assertEquals("Regular checkup", result.getMotive());
        assertEquals("Healthy", result.getDiagnosis());
        assertEquals("Vaccine", result.getMedicine());
    }

    @Test
    void toDomain_withNullOptionalFields_shouldNotSetThem() throws Exception {
        // Arrange
        request.setMotive(null);
        request.setDiagnosis(null);
        request.setMedicine(null);
        request.setDoce(null);

        when(clinicalRecordBuilder.create(anyString(), anyString(), anyString()))
            .thenReturn(record);

        // Act
        ClinicalRecord result = clinicalRecordRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        verify(clinicalRecordBuilder).create("222222", "10", "50");
        verify(clinicalRecordValidator, never()).motiveValidator(anyString());
        verify(clinicalRecordValidator, never()).diagnosisValidator(anyString());
        verify(clinicalRecordValidator, never()).medicineValidator(anyString());
        verify(clinicalRecordValidator, never()).doceValidator(anyString());
    }

    @Test
    void toResponse_shouldMapAllFields() {
        // Act
        ClinicalRecordResponse result = clinicalRecordRestMapper.toResponse(record);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(10L, result.getPetId());
        assertEquals(222222L, result.getVeterinarianDocument());
        assertEquals(50L, result.getOrderId());
        assertNotNull(result.getDate());
        assertTrue(result.isStatus());
        assertEquals("Regular checkup", result.getMotive());
        assertEquals("Healthy", result.getDiagnosis());
    assertEquals("Vaccine", result.getMedicine());
    }

    @Test
    void toResponse_withNullPet_shouldMapPetIdAsZero() {
        // Arrange
        record.setPet(null);

        // Act
        ClinicalRecordResponse result = clinicalRecordRestMapper.toResponse(record);

        // Assert
        assertEquals(0L, result.getPetId());
    }

    @Test
    void toResponse_withNullVeterinarian_shouldMapVeterinarianDocumentAsZero() {
        // Arrange
        record.setVeterinarian(null);

        // Act
        ClinicalRecordResponse result = clinicalRecordRestMapper.toResponse(record);

        // Assert
        assertEquals(0L, result.getVeterinarianDocument());
    }

    @Test
    void toResponse_withNullClinicalOrder_shouldMapOrderIdAsZero() {
        // Arrange
        record.setClinicalOrder(null);

        // Act
        ClinicalRecordResponse result = clinicalRecordRestMapper.toResponse(record);

        // Assert
        assertEquals(0L, result.getOrderId());
    }

    @Test
    void toResponse_withNullOptionalFields_shouldMapThemAsNull() {
        // Arrange
        record.setMotive(null);
        record.setDiagnosis(null);
        record.setMedicine(null);
        record.setDoce(null);

        // Act
        ClinicalRecordResponse result = clinicalRecordRestMapper.toResponse(record);

        // Assert
        assertNull(result.getMotive());
        assertNull(result.getDiagnosis());
        assertNull(result.getMedicine());
    }
}
