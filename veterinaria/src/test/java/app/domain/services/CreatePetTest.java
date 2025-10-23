package app.domain.services;

import app.domain.model.Pet;
import app.domain.model.User;
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
class CreatePetTest {

    @Mock
    private UserPort userPort;

    @Mock
    private PetPort petPort;

    @InjectMocks
    private CreatePet createPet;

    private Pet testPet;
    private User testOwner;

    @BeforeEach
    void setUp() {
        testOwner = new User();
        testOwner.setDocument(123456789L);
        testOwner.setName("Owner Name");

        testPet = new Pet();
        testPet.setName("Rex");
        testPet.setAge(5);
        testPet.setWeigth(25.5);
        
        User ownerWithDocument = new User();
        ownerWithDocument.setDocument(123456789L);
        testPet.setOwner(ownerWithDocument);
    }

    @Test
    void create_withValidOwner_shouldSetOwnerAndSavePet() throws Exception {
        // Arrange
        when(userPort.findByDocument(testPet.getOwner())).thenReturn(testOwner);

        // Act
        createPet.create(testPet);

        // Assert
        assertEquals(testOwner, testPet.getOwner());
        verify(userPort).findByDocument(any(User.class));
        verify(petPort).save(testPet);
    }

    @Test
    void create_withNullOwner_shouldThrowException() throws Exception {
        // Arrange
        when(userPort.findByDocument(testPet.getOwner())).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createPet.create(testPet)
        );
        assertTrue(ex.getMessage().contains("dueño valido"));
        verify(userPort).findByDocument(any(User.class));
        verify(petPort, never()).save(any());
    }

    @Test
    void create_withInvalidOwnerDocument_shouldThrowException() throws Exception {
        // Arrange
        User invalidOwner = new User();
        invalidOwner.setDocument(999999L);
        testPet.setOwner(invalidOwner);
        when(userPort.findByDocument(invalidOwner)).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createPet.create(testPet)
        );
        assertTrue(ex.getMessage().contains("dueño valido"));
        verify(petPort, never()).save(any());
    }

    @Test
    void create_shouldReplaceOwnerWithFoundOwner() throws Exception {
        // Arrange
        User originalOwner = new User();
        originalOwner.setDocument(123456789L);
        testPet.setOwner(originalOwner);
        
        User foundOwner = new User();
        foundOwner.setDocument(123456789L);
        foundOwner.setName("Full Owner Data");
        foundOwner.setAge(40);
        
        when(userPort.findByDocument(originalOwner)).thenReturn(foundOwner);

        // Act
        createPet.create(testPet);

        // Assert
        assertSame(foundOwner, testPet.getOwner());
        assertEquals("Full Owner Data", testPet.getOwner().getName());
        verify(petPort).save(testPet);
    }
}
