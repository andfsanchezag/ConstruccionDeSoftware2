package app.infrastructure.persistence.mapper;

import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

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
    void toEntity_shouldMapAllFields() {
        // Act
        UserEntity result = UserMapper.toEntity(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(123456789L, result.getDocument());
        assertEquals(30, result.getAge());
        assertEquals("VETERINARIAN", result.getRole());
        assertEquals("johndoe", result.getUserName());
        assertEquals("password123", result.getPassword());
    }

    @Test
    void toEntity_withNullUser_shouldReturnNull() {
        // Act
        UserEntity result = UserMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomain_shouldMapAllFields() {
        // Act
        User result = UserMapper.toDomain(userEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(123456789L, result.getDocument());
        assertEquals(30, result.getAge());
        assertEquals(Role.VETERINARIAN, result.getRole());
        assertEquals("johndoe", result.getUserName());
        assertEquals("password123", result.getPassword());
    }

    @Test
    void toDomain_withNullEntity_shouldReturnNull() {
        // Act
        User result = UserMapper.toDomain(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_withAllRoles_shouldMapCorrectly() {
        // Test ADMIN
        user.setRole(Role.ADMIN);
        assertEquals("ADMIN", UserMapper.toEntity(user).getRole());

        // Test SELLER
        user.setRole(Role.SELLER);
        assertEquals("SELLER", UserMapper.toEntity(user).getRole());

        // Test OWNER
        user.setRole(Role.OWNER);
        assertEquals("OWNER", UserMapper.toEntity(user).getRole());
    }

    @Test
    void toDomain_withAllRoles_shouldMapCorrectly() {
        // Test ADMIN
        userEntity.setRole("ADMIN");
        assertEquals(Role.ADMIN, UserMapper.toDomain(userEntity).getRole());

        // Test SELLER
        userEntity.setRole("SELLER");
        assertEquals(Role.SELLER, UserMapper.toDomain(userEntity).getRole());

        // Test OWNER
        userEntity.setRole("OWNER");
        assertEquals(Role.OWNER, UserMapper.toDomain(userEntity).getRole());
    }
}
