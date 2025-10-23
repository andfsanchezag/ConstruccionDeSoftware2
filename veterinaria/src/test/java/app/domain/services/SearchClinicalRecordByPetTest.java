package app.domain.services;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.PetPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchClinicalRecordByPetTest {

    @Mock
    private PetPort petPort;

    @Mock
    private ClinicalRecordPort clinicalRecordPort;

    @InjectMocks
    private SearchClinicalRecordByPet searchClinicalRecordByPet;

    private Pet testPet;
    private List<ClinicalRecord> records;

    @BeforeEach
    void setUp() {
        testPet = new Pet();
        testPet.setId(20L);
        testPet.setName("Fluffy");

        ClinicalRecord record1 = new ClinicalRecord();
        record1.setId(1L);
        ClinicalRecord record2 = new ClinicalRecord();
        record2.setId(2L);
        records = Arrays.asList(record1, record2);
    }

    @Test
    void search_withValidPet_shouldReturnRecords() throws Exception {
        // Arrange
        when(petPort.findById(testPet)).thenReturn(testPet);
        when(clinicalRecordPort.findByPet(testPet)).thenReturn(records);

        // Act
        List<ClinicalRecord> result = searchClinicalRecordByPet.search(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(petPort).findById(testPet);
        verify(clinicalRecordPort).findByPet(testPet);
    }

    @Test
    void search_withNonExistentPet_shouldThrowException() throws Exception {
        // Arrange
        when(petPort.findById(testPet)).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            searchClinicalRecordByPet.search(testPet)
        );
        assertTrue(ex.getMessage().contains("no existe la mascota"));
        verify(petPort).findById(testPet);
        verify(clinicalRecordPort, never()).findByPet(any());
    }

    @Test
    void search_withPetWithoutRecords_shouldReturnEmptyList() throws Exception {
        // Arrange
        when(petPort.findById(testPet)).thenReturn(testPet);
        when(clinicalRecordPort.findByPet(testPet)).thenReturn(Arrays.asList());

        // Act
        List<ClinicalRecord> result = searchClinicalRecordByPet.search(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
