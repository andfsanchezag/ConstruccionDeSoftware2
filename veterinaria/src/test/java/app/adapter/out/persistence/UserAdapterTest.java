package app.adapter.out.persistence;

import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.infrastructure.persistence.entities.UserEntity;
import app.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAdapter userAdapter;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setDocument(123456789L);
        user.setAge(30);
        user.setRole(Role.VETERINARIAN);
        user.setUserName("johndoe");
        user.setPassword("password123");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");
        userEntity.setDocument(123456789L);
        userEntity.setAge(30);
        userEntity.setRole("VETERINARIAN");
        userEntity.setUserName("johndoe");
        userEntity.setPassword("password123");
    }

    @Test
    void findByDocument_shouldReturnUser() throws Exception {
        // Arrange
        when(userRepository.findByDocument(123456789L)).thenReturn(userEntity);

        // Act
        User result = userAdapter.findByDocument(user);

        // Assert
        assertNotNull(result);
        assertEquals(123456789L, result.getDocument());
        assertEquals("John Doe", result.getName());
        verify(userRepository).findByDocument(123456789L);
    }

    @Test
    void findByDocument_whenNotFound_shouldReturnNull() throws Exception {
        // Arrange
        when(userRepository.findByDocument(123456789L)).thenReturn(null);

        // Act
        User result = userAdapter.findByDocument(user);

        // Assert
        assertNull(result);
        verify(userRepository).findByDocument(123456789L);
    }

    @Test
    void findByUserName_shouldReturnUser() throws Exception {
        // Arrange
        when(userRepository.findByUserName("johndoe")).thenReturn(userEntity);

        // Act
        User result = userAdapter.findByUserName(user);

        // Assert
        assertNotNull(result);
        assertEquals("johndoe", result.getUserName());
        assertEquals("John Doe", result.getName());
        verify(userRepository).findByUserName("johndoe");
    }

    @Test
    void findByUserName_whenNotFound_shouldReturnNull() throws Exception {
        // Arrange
        when(userRepository.findByUserName("johndoe")).thenReturn(null);

        // Act
        User result = userAdapter.findByUserName(user);

        // Assert
        assertNull(result);
        verify(userRepository).findByUserName("johndoe");
    }

    @Test
    void save_shouldCallRepository() throws Exception {
        // Arrange
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        userAdapter.save(user);

        // Assert
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void save_shouldConvertToEntityBeforeSaving() throws Exception {
        // Arrange
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        userAdapter.save(user);

        // Assert
        verify(userRepository).save(argThat(entity ->
            entity.getDocument() == 123456789L &&
            entity.getName().equals("John Doe") &&
            entity.getRole().equals("VETERINARIAN")
        ));
    }
}
