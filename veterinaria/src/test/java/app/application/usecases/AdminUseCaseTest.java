package app.application.usecases;

import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.services.CreateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUseCaseTest {

    @Mock
    private CreateUser createUser;

    @InjectMocks
    private AdminUseCase adminUseCase;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setDocument(123456L);
        testUser.setName("Test User");
        testUser.setUserName("testuser");
        testUser.setPassword("pass123");
        testUser.setAge(30);
    }

    @Test
    void createVeterinarian_shouldSetRoleAndCallCreateUser() throws Exception {
        // Act
        adminUseCase.createVeterinarian(testUser);

        // Assert
        assertEquals(Role.VETERINARIAN, testUser.getRole());
        verify(createUser).create(testUser);
    }

    @Test
    void createSeller_shouldSetRoleAndCallCreateUser() throws Exception {
        // Act
        adminUseCase.createSeller(testUser);

        // Assert
        assertEquals(Role.SELLER, testUser.getRole());
        verify(createUser).create(testUser);
    }

    @Test
    void createVeterinarian_withExistingRole_shouldOverrideRole() throws Exception {
        // Arrange
        testUser.setRole(Role.ADMIN);

        // Act
        adminUseCase.createVeterinarian(testUser);

        // Assert
        assertEquals(Role.VETERINARIAN, testUser.getRole());
        verify(createUser).create(testUser);
    }

    @Test
    void createSeller_withExistingRole_shouldOverrideRole() throws Exception {
        // Arrange
        testUser.setRole(Role.OWNER);

        // Act
        adminUseCase.createSeller(testUser);

        // Assert
        assertEquals(Role.SELLER, testUser.getRole());
        verify(createUser).create(testUser);
    }

    @Test
    void createVeterinarian_whenCreateUserThrows_shouldPropagateException() throws Exception {
        // Arrange
        doThrow(new Exception("Create failed")).when(createUser).create(testUser);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            adminUseCase.createVeterinarian(testUser)
        );
        assertEquals(Role.VETERINARIAN, testUser.getRole());
    }
}
