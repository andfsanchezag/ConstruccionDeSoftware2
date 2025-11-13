package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.ports.ClinicalOrderPort;
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
class CreateClinicalOrderTest {

    @Mock
    private UserPort userPort;

    @Mock
    private PetPort petPort;

    @Mock
    private ClinicalOrderPort clinicalOrderPort;

    @InjectMocks
    private CreateClinicalOrder createClinicalOrder;

    private ClinicalOrder testOrder;
    private User veterinarian;
    private Pet pet;
    private User owner;

    @BeforeEach
    void setUp() {
        veterinarian = new User();
        veterinarian.setDocument(111111L);
        veterinarian.setRole(Role.VETERINARIAN);

        owner = new User();
        owner.setDocument(222222L);
        owner.setName("Owner");

        pet = new Pet();
        pet.setId(100L);
        pet.setName("Rex");
        pet.setOwner(owner);

        testOrder = new ClinicalOrder();
        User vetRef = new User();
        vetRef.setDocument(111111L);
        testOrder.setVeterinarian(vetRef);
        
        Pet petRef = new Pet();
        petRef.setId(100L);
        testOrder.setPet(petRef);
        
        testOrder.setMedicine("Antibiotic");
        testOrder.setDoce("500mg");
    }

    @Test
    void create_withValidVeterinarianAndPet_shouldSaveOrder() throws Exception {
        // Arrange
        when(userPort.findByDocument(any(User.class))).thenReturn(veterinarian);
        when(petPort.findById(any(Pet.class))).thenReturn(pet);

        // Act
        createClinicalOrder.create(testOrder);

        // Assert
        assertNotNull(testOrder.getDate());
        assertEquals(pet, testOrder.getPet());
        assertEquals(owner, testOrder.getOwner());
        assertEquals(veterinarian, testOrder.getVeterinarian());
        verify(userPort).findByDocument(any(User.class));
        verify(petPort).findById(any(Pet.class));
        verify(clinicalOrderPort).save(testOrder);
    }

    @Test
    void create_withNullVeterinarian_shouldThrowException() throws Exception {
        // Arrange
        when(userPort.findByDocument(any(User.class))).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createClinicalOrder.create(testOrder)
        );
        assertTrue(ex.getMessage().contains("veterinarios"));
        verify(clinicalOrderPort, never()).save(any());
    }

    @Test
    void create_withNonVeterinarianUser_shouldThrowException() throws Exception {
        // Arrange
        User seller = new User();
        seller.setDocument(111111L);
        seller.setRole(Role.SELLER);
        when(userPort.findByDocument(any(User.class))).thenReturn(seller);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createClinicalOrder.create(testOrder)
        );
        assertTrue(ex.getMessage().contains("veterinarios"));
        verify(clinicalOrderPort, never()).save(any());
    }

    @Test
    void create_withNullPet_shouldThrowException() throws Exception {
        // Arrange
        when(userPort.findByDocument(any(User.class))).thenReturn(veterinarian);
        when(petPort.findById(any(Pet.class))).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createClinicalOrder.create(testOrder)
        );
        assertTrue(ex.getMessage().contains("mascotas registradas"));
        verify(clinicalOrderPort, never()).save(any());
    }

    @Test
    void create_shouldSetDateAutomatically() throws Exception {
        // Arrange
        when(userPort.findByDocument(any(User.class))).thenReturn(veterinarian);
        when(petPort.findById(any(Pet.class))).thenReturn(pet);

        // Act
        createClinicalOrder.create(testOrder);

        // Assert
        assertNotNull(testOrder.getDate());
    }

    @Test
    void create_shouldSetOwnerFromPet() throws Exception {
        // Arrange
        when(userPort.findByDocument(any(User.class))).thenReturn(veterinarian);
        when(petPort.findById(any(Pet.class))).thenReturn(pet);

        // Act
        createClinicalOrder.create(testOrder);

        // Assert
        assertEquals(owner, testOrder.getOwner());
        assertEquals(222222L, testOrder.getOwner().getDocument());
    }
}
