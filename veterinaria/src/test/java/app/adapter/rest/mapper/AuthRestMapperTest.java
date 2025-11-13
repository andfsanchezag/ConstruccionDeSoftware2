package app.adapter.rest.mapper;

import app.adapter.rest.request.AuthRequest;
import app.adapter.rest.response.TokenResponseDto;
import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthRestMapperTest {

    private AuthRestMapper authRestMapper;

    @BeforeEach
    void setUp() {
        authRestMapper = new AuthRestMapper();
    }

    @Test
    void toDomain_shouldMapAuthRequestToAuthCredentials() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("pass123");

        // Act
        AuthCredentials result = authRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("pass123", result.getPassword());
    }

    @Test
    void toDomain_withNullValues_shouldMapNullFields() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setUsername(null);
        request.setPassword(null);

        // Act
        AuthCredentials result = authRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        assertNull(result.getUsername());
        assertNull(result.getPassword());
    }

    @Test
    void toResponse_shouldMapTokenResponseToDto() {
        // Arrange
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("jwt-token-xyz");

        // Act
        TokenResponseDto result = authRestMapper.toResponse(tokenResponse);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token-xyz", result.getToken());
    }

    @Test
    void toResponse_withNullToken_shouldMapNullToken() {
        // Arrange
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(null);

        // Act
        TokenResponseDto result = authRestMapper.toResponse(tokenResponse);

        // Assert
        assertNotNull(result);
        assertNull(result.getToken());
    }
}
