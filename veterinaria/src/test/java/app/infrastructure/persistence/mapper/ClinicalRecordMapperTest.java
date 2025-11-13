package app.infrastructure.persistence.mapper;

import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;
import app.infrastructure.persistence.entities.ClinicalOrderEntity;
import app.infrastructure.persistence.entities.ClinicalRecordEntity;
import app.infrastructure.persistence.entities.PetEntity;
import app.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClinicalRecordMapperTest {

    private ClinicalRecord record;
    private ClinicalRecordEntity recordEntity;
    private Pet pet;
    private PetEntity petEntity;
    private User veterinarian;
    private UserEntity veterinarianEntity;
    private ClinicalOrder clinicalOrder;
    private ClinicalOrderEntity clinicalOrderEntity;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date(System.currentTimeMillis());

        veterinarian = new User();
        veterinarian.setId(1L);
        veterinarian.setDocument(123456L);

        veterinarianEntity = new UserEntity();
        veterinarianEntity.setId(1L);
        veterinarianEntity.setDocument(123456L);

        pet = new Pet();
        pet.setId(10L);

        petEntity = new PetEntity();
        petEntity.setId(10L);

        clinicalOrder = new ClinicalOrder();
        clinicalOrder.setId(50L);

        clinicalOrderEntity = new ClinicalOrderEntity();
        clinicalOrderEntity.setId(50L);

        record = new ClinicalRecord();
        record.setId(100L);
        record.setPet(pet);
        record.setVeterinarian(veterinarian);
        record.setDate(testDate);
        record.setMotive("Checkup");
        record.setDiagnosis("Healthy");
        record.setMedicine("Vaccine");
        record.setMedicalProcedure("Vaccination");
        record.setDoce("1ml");
        record.setClinicalOrder(clinicalOrder);
        record.setVaccinationRecord("Rabies vaccine");
        record.setAllergies("None");
        record.setProceddureDetail("Standard procedure");
        record.setSymptoms("None");
        record.setStatus(true);

        recordEntity = new ClinicalRecordEntity();
        recordEntity.setId(100L);
        recordEntity.setPet(petEntity);
        recordEntity.setVeterinarian(veterinarianEntity);
        recordEntity.setDate(testDate);
        recordEntity.setMotive("Checkup");
        recordEntity.setDiagnosis("Healthy");
        recordEntity.setMedicine("Vaccine");
        recordEntity.setMedicalProcedure("Vaccination");
        recordEntity.setDose("1ml");
        recordEntity.setClinicalOrder(clinicalOrderEntity);
        recordEntity.setVaccinationRecord("Rabies vaccine");
        recordEntity.setAllergies("None");
        recordEntity.setProcedureDetail("Standard procedure");
        recordEntity.setSymptoms("None");
        recordEntity.setStatus(true);
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Act
        ClinicalRecordEntity result = ClinicalRecordMapper.toEntity(record);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Checkup", result.getMotive());
        assertEquals("Healthy", result.getDiagnosis());
        assertEquals("Vaccine", result.getMedicine());
        assertEquals("Vaccination", result.getMedicalProcedure());
        assertEquals("1ml", result.getDose());
        assertEquals("Rabies vaccine", result.getVaccinationRecord());
        assertEquals("None", result.getAllergies());
        assertEquals("Standard procedure", result.getProcedureDetail());
        assertEquals("None", result.getSymptoms());
        assertTrue(result.isStatus());
        assertEquals(testDate, result.getDate());
        assertNotNull(result.getPet());
        assertEquals(10L, result.getPet().getId());
        assertNotNull(result.getVeterinarian());
        assertEquals(123456L, result.getVeterinarian().getDocument());
        assertNotNull(result.getClinicalOrder());
        assertEquals(50L, result.getClinicalOrder().getId());
    }

    @Test
    void toEntity_withNullRecord_shouldReturnNull() {
        // Act
        ClinicalRecordEntity result = ClinicalRecordMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomain_shouldMapAllFields() {
        // Act
        ClinicalRecord result = ClinicalRecordMapper.toDomain(recordEntity);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Checkup", result.getMotive());
        assertEquals("Healthy", result.getDiagnosis());
        assertEquals("Vaccine", result.getMedicine());
        assertEquals("Vaccination", result.getMedicalProcedure());
        assertEquals("1ml", result.getDoce());
        assertEquals("Rabies vaccine", result.getVaccinationRecord());
        assertEquals("None", result.getAllergies());
        assertEquals("Standard procedure", result.getProceddureDetail());
        assertEquals("None", result.getSymptoms());
        assertTrue(result.isStatus());
        assertEquals(testDate, result.getDate());
        assertNotNull(result.getPet());
        assertEquals(10L, result.getPet().getId());
        assertNotNull(result.getVeterinarian());
        assertEquals(123456L, result.getVeterinarian().getDocument());
        assertNotNull(result.getClinicalOrder());
        assertEquals(50L, result.getClinicalOrder().getId());
    }

    @Test
    void toDomain_withNullEntity_shouldReturnNull() {
        // Act
        ClinicalRecord result = ClinicalRecordMapper.toDomain(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_shouldHandleDoseFieldCorrectly() {
        // The mapper converts "doce" (domain) to "dose" (entity)
        record.setDoce("2ml");

        // Act
        ClinicalRecordEntity result = ClinicalRecordMapper.toEntity(record);

        // Assert
        assertEquals("2ml", result.getDose());
    }

    @Test
    void toDomain_shouldHandleDoceFieldCorrectly() {
        // The mapper converts "dose" (entity) to "doce" (domain)
        recordEntity.setDose("2ml");

        // Act
        ClinicalRecord result = ClinicalRecordMapper.toDomain(recordEntity);

        // Assert
        assertEquals("2ml", result.getDoce());
    }

    @Test
    void toEntity_shouldHandleProcedureDetailFieldCorrectly() {
        // The mapper converts "proceddureDetail" (domain typo) to "procedureDetail" (entity)
        record.setProceddureDetail("Detailed procedure");

        // Act
        ClinicalRecordEntity result = ClinicalRecordMapper.toEntity(record);

        // Assert
        assertEquals("Detailed procedure", result.getProcedureDetail());
    }

    @Test
    void toDomain_shouldHandleProceddureDetailFieldCorrectly() {
        // The mapper converts "procedureDetail" (entity) to "proceddureDetail" (domain typo)
        recordEntity.setProcedureDetail("Detailed procedure");

        // Act
        ClinicalRecord result = ClinicalRecordMapper.toDomain(recordEntity);

        // Assert
        assertEquals("Detailed procedure", result.getProceddureDetail());
    }
}
