package app.adapter.rest.mapper;

import app.adapter.in.builder.UserBuilder;
import app.adapter.rest.request.CreateUserRequest;
import app.adapter.rest.response.UserResponse;
import app.domain.model.User;
import app.domain.model.emuns.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRestMapperTest {

    @Mock
    private UserBuilder userBuilder;

    @InjectMocks
    private UserRestMapper userRestMapper;

    private CreateUserRequest request;
    private User user;

    @BeforeEach
    void setUp() {
        request = new CreateUserRequest();
        request.setName("John Doe");
        request.setDocument("123456789");
        request.setAge("30");
        request.setUserName("jdoe");
        request.setPassword("pass123");

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setDocument(123456789L);
        user.setAge(30);
        user.setUserName("jdoe");
        user.setPassword("pass123");
        user.setRole(Role.ADMIN);
    }

    @Test
    void toDomain_shouldCallBuilderWithCorrectParams() throws Exception {
        // Arrange
        when(userBuilder.build(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(user);

        // Act
        User result = userRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        verify(userBuilder).build("John Doe", "123456789", "30", "jdoe", "pass123");
    }

    @Test
    void toDomain_whenBuilderThrows_shouldPropagateException() throws Exception {
        // Arrange
        when(userBuilder.build(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenThrow(new Exception("Build failed"));

        // Act & Assert
        assertThrows(Exception.class, () -> 
            userRestMapper.toDomain(request)
        );
    }

    @Test
    void toResponse_shouldMapAllFields() {
        // Act
        UserResponse result = userRestMapper.toResponse(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(123456789L, result.getDocument());
        assertEquals(30, result.getAge());
        assertEquals("ADMIN", result.getRole());
        assertEquals("jdoe", result.getUserName());
    }

    @Test
    void toResponse_withNullRole_shouldMapRoleAsNull() {
        // Arrange
        user.setRole(null);

        // Act
        UserResponse result = userRestMapper.toResponse(user);

        // Assert
        assertNull(result.getRole());
    }

    @Test
    void toResponse_withAllRoleTypes_shouldMapCorrectly() {
        for (Role role : Role.values()) {
            user.setRole(role);
            UserResponse result = userRestMapper.toResponse(user);
            assertEquals(String.valueOf(role), result.getRole());
        }
    }
}
