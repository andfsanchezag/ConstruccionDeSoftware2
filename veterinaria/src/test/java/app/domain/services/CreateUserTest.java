package app.domain.services;

import app.application.exceptions.BusinessException;
import app.domain.model.User;
import app.domain.model.emuns.Role;
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
class CreateUserTest {

    @Mock
    private UserPort userPort;

    @InjectMocks
    private CreateUser createUser;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setDocument(123456789L);
        testUser.setName("Test User");
        testUser.setUserName("testuser");
        testUser.setPassword("pass123");
        testUser.setAge(30);
    }

    @Test
    void create_withUniqueUserAndOwnerRole_shouldSaveUser() throws Exception {
        // Arrange
        testUser.setRole(Role.OWNER);
        when(userPort.findByDocument(testUser)).thenReturn(null);

        // Act
        createUser.create(testUser);

        // Assert
        verify(userPort).findByDocument(testUser);
        verify(userPort).save(testUser);
        verify(userPort, never()).findByUserName(any());
    }

    @Test
    void create_withUniqueUserAndVeterinarianRole_shouldCheckUsernameAndSave() throws Exception {
        // Arrange
        testUser.setRole(Role.VETERINARIAN);
        when(userPort.findByDocument(testUser)).thenReturn(null);
        when(userPort.findByUserName(testUser)).thenReturn(null);

        // Act
        createUser.create(testUser);

        // Assert
        verify(userPort).findByDocument(testUser);
        verify(userPort).findByUserName(testUser);
        verify(userPort).save(testUser);
    }

    @Test
    void create_withDuplicateDocument_shouldThrowBusinessException() throws Exception {
        // Arrange
        testUser.setRole(Role.ADMIN);
        User existingUser = new User();
        existingUser.setDocument(123456789L);
        when(userPort.findByDocument(testUser)).thenReturn(existingUser);

        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class, () -> 
            createUser.create(testUser)
        );
        assertTrue(ex.getMessage().contains("cedula"));
        verify(userPort).findByDocument(testUser);
        verify(userPort, never()).save(any());
    }

    @Test
    void create_withDuplicateUsernameForNonOwner_shouldThrowBusinessException() throws Exception {
        // Arrange
        testUser.setRole(Role.SELLER);
        when(userPort.findByDocument(testUser)).thenReturn(null);
        User existingUser = new User();
        existingUser.setUserName("testuser");
        when(userPort.findByUserName(testUser)).thenReturn(existingUser);

        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class, () -> 
            createUser.create(testUser)
        );
        assertTrue(ex.getMessage().contains("nombre de usuario"));
        verify(userPort).findByDocument(testUser);
        verify(userPort).findByUserName(testUser);
        verify(userPort, never()).save(any());
    }

    @Test
    void create_withAdminRole_shouldCheckUsernameAndSave() throws Exception {
        // Arrange
        testUser.setRole(Role.ADMIN);
        when(userPort.findByDocument(testUser)).thenReturn(null);
        when(userPort.findByUserName(testUser)).thenReturn(null);

        // Act
        createUser.create(testUser);

        // Assert
        verify(userPort).findByDocument(testUser);
        verify(userPort).findByUserName(testUser);
        verify(userPort).save(testUser);
    }

    @Test
    void create_withSellerRole_shouldCheckUsernameAndSave() throws Exception {
        // Arrange
        testUser.setRole(Role.SELLER);
        when(userPort.findByDocument(testUser)).thenReturn(null);
        when(userPort.findByUserName(testUser)).thenReturn(null);

        // Act
        createUser.create(testUser);

        // Assert
        verify(userPort).findByDocument(testUser);
        verify(userPort).findByUserName(testUser);
        verify(userPort).save(testUser);
    }
}
