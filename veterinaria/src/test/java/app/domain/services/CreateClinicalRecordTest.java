package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.PetPort;
import app.domain.ports.UserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClinicalRecordTest {

    @Mock
    private UserPort userPort;

    @Mock
    private PetPort petPort;

    @Mock
    private ClinicalOrderPort clinicalOrderPort;

    @Mock
    private ClinicalRecordPort clinicalRecordPort;

    @InjectMocks
    private CreateClinicalRecord createClinicalRecord;

    private ClinicalRecord testRecord;
    private User veterinarian;
    private Pet pet;
    private ClinicalOrder order;

    @BeforeEach
    void setUp() {
        veterinarian = new User();
        veterinarian.setDocument(111111L);
        veterinarian.setRole(Role.VETERINARIAN);

        pet = new Pet();
        pet.setId(100L);
        pet.setName("Rex");

        order = new ClinicalOrder();
        order.setId(500L);

        testRecord = new ClinicalRecord();
        
        Pet petRef = new Pet();
        petRef.setId(100L);
        testRecord.setPet(petRef);
        
        User vetRef = new User();
        vetRef.setDocument(111111L);
        testRecord.setVeterinarian(vetRef);
        
        ClinicalOrder orderRef = new ClinicalOrder();
        orderRef.setId(500L);
        testRecord.setClinicalOrder(orderRef);
    }

    @Test
    void create_withValidData_shouldSaveRecord() throws Exception {
        // Arrange
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(userPort.findByDocument(any(User.class))).thenReturn(veterinarian);
        when(clinicalOrderPort.findById(any(ClinicalOrder.class))).thenReturn(order);

        // Act
        createClinicalRecord.create(testRecord);

        // Assert
        assertEquals(pet, testRecord.getPet());
        assertEquals(veterinarian, testRecord.getVeterinarian());
        assertEquals(order, testRecord.getClinicalOrder());
        verify(petPort).findById(any(Pet.class));
        verify(userPort).findByDocument(any(User.class));
        verify(clinicalOrderPort).findById(any(ClinicalOrder.class));
        verify(clinicalRecordPort).save(testRecord);
    }

    @Test
    void create_withNullPet_shouldThrowException() throws Exception {
        // Arrange
        when(petPort.findById(any(Pet.class))).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createClinicalRecord.create(testRecord)
        );
        assertTrue(ex.getMessage().contains("mascota valida"));
        verify(clinicalRecordPort, never()).save(any());
    }

    @Test
    void create_withNullVeterinarian_shouldThrowException() throws Exception {
        // Arrange
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(userPort.findByDocument(any(User.class))).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createClinicalRecord.create(testRecord)
        );
        assertTrue(ex.getMessage().contains("veterinario valido"));
        verify(clinicalRecordPort, never()).save(any());
    }

    @Test
    void create_withNonVeterinarianUser_shouldThrowException() throws Exception {
        // Arrange
        User admin = new User();
        admin.setDocument(111111L);
        admin.setRole(Role.ADMIN);
        
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(userPort.findByDocument(any(User.class))).thenReturn(admin);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createClinicalRecord.create(testRecord)
        );
        assertTrue(ex.getMessage().contains("veterinario valido"));
        verify(clinicalRecordPort, never()).save(any());
    }

    @Test
    void create_withNullClinicalOrder_shouldThrowException() throws Exception {
        // Arrange
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(userPort.findByDocument(any(User.class))).thenReturn(veterinarian);
        when(clinicalOrderPort.findById(any(ClinicalOrder.class))).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createClinicalRecord.create(testRecord)
        );
        assertTrue(ex.getMessage().contains("orden valida"));
        verify(clinicalRecordPort, never()).save(any());
    }

    @Test
    void create_shouldSetAllEntitiesFromPorts() throws Exception {
        // Arrange
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(userPort.findByDocument(any(User.class))).thenReturn(veterinarian);
        when(clinicalOrderPort.findById(any(ClinicalOrder.class))).thenReturn(order);

        // Act
        createClinicalRecord.create(testRecord);

        // Assert
        assertSame(pet, testRecord.getPet());
        assertSame(veterinarian, testRecord.getVeterinarian());
        assertSame(order, testRecord.getClinicalOrder());
    }
}
