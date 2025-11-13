package app.adapter.out.persistence;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.infrastructure.persistence.entities.ClinicalRecordEntity;
import app.infrastructure.persistence.repository.ClinicalRecordRepository;
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
class ClinicalRecordAdapterTest {

    @Mock
    private ClinicalRecordRepository clinicalRecordRepository;

    @InjectMocks
    private ClinicalRecordAdapter clinicalRecordAdapter;

    private ClinicalRecord record;
    private ClinicalRecordEntity recordEntity;
    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(10L);

        record = new ClinicalRecord();
        record.setId(100L);
        record.setPet(pet);
        record.setDate(new Date(System.currentTimeMillis()));
        record.setMotive("Checkup");
        record.setDiagnosis("Healthy");
        record.setStatus(true);

        recordEntity = new ClinicalRecordEntity();
        recordEntity.setId(100L);
        recordEntity.setMotive("Checkup");
        recordEntity.setDiagnosis("Healthy");
    }

    @Test
    void save_shouldCallRepository() throws Exception {
        // Arrange
        when(clinicalRecordRepository.save(any(ClinicalRecordEntity.class)))
            .thenReturn(recordEntity);

        // Act
        clinicalRecordAdapter.save(record);

        // Assert
        verify(clinicalRecordRepository).save(any(ClinicalRecordEntity.class));
    }

    @Test
    void save_shouldConvertToEntityBeforeSaving() throws Exception {
        // Arrange
        when(clinicalRecordRepository.save(any(ClinicalRecordEntity.class)))
            .thenReturn(recordEntity);

        // Act
        clinicalRecordAdapter.save(record);

        // Assert
        verify(clinicalRecordRepository).save(argThat(entity ->
            entity.getId() == 100L &&
            entity.getMotive().equals("Checkup") &&
            entity.getDiagnosis().equals("Healthy")
        ));
    }

    @Test
    void findByPet_shouldReturnNull() throws Exception {
        // This method is not implemented in the adapter (returns null)
        
        // Act
        var result = clinicalRecordAdapter.findByPet(pet);

        // Assert
        assertNull(result);
        verifyNoInteractions(clinicalRecordRepository);
    }

    @Test
    void save_shouldHandleCompleteRecord() throws Exception {
        // Arrange
        record.setMedicine("Vaccine");
        record.setDoce("1ml");
        record.setAllergies("None");
        record.setSymptoms("None");
        
        when(clinicalRecordRepository.save(any(ClinicalRecordEntity.class)))
            .thenReturn(recordEntity);

        // Act
        clinicalRecordAdapter.save(record);

        // Assert
        verify(clinicalRecordRepository).save(any(ClinicalRecordEntity.class));
    }
}
