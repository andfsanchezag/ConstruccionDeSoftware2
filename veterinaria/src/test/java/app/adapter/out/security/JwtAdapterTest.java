package app.adapter.out.security;

import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtAdapterTest {

    @InjectMocks
    private JwtAdapter jwtAdapter;

    private AuthCredentials credentials;

    @BeforeEach
    void setUp() {
        credentials = new AuthCredentials();
        credentials.setUsername("testuser");
        credentials.setPassword("password123");
    }

    @Test
    void authenticate_shouldReturnTokenResponse() {
        // Act
        TokenResponse result = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");

        // Assert
        assertNotNull(result);
        assertNotNull(result.getToken());
        assertFalse(result.getToken().isEmpty());
    }

    @Test
    void authenticate_withDifferentRoles_shouldGenerateDifferentTokens() {
        // Act
        TokenResponse adminToken = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");
        TokenResponse userToken = jwtAdapter.authenticate(credentials, "ROLE_USER");

        // Assert
        assertNotNull(adminToken.getToken());
        assertNotNull(userToken.getToken());
        assertNotEquals(adminToken.getToken(), userToken.getToken());
    }

    @Test
    void validateToken_withValidToken_shouldReturnTrue() {
        // Arrange
        TokenResponse tokenResponse = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");
        String token = tokenResponse.getToken();

        // Act
        boolean result = jwtAdapter.validateToken(token);

        // Assert
        assertTrue(result);
    }

    @Test
    void validateToken_withInvalidToken_shouldReturnFalse() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        boolean result = jwtAdapter.validateToken(invalidToken);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateToken_withEmptyToken_shouldReturnFalse() {
        // Act
        boolean result = jwtAdapter.validateToken("");

        // Assert
        assertFalse(result);
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        // Arrange
        TokenResponse tokenResponse = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");
        String token = tokenResponse.getToken();

        // Act
        String username = jwtAdapter.extractUsername(token);

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {
        // Arrange
        TokenResponse tokenResponse = jwtAdapter.authenticate(credentials, "ROLE_VETERINARIAN");
        String token = tokenResponse.getToken();

        // Act
        String role = jwtAdapter.extractRole(token);

        // Assert
        assertEquals("ROLE_VETERINARIAN", role);
    }

    @Test
    void extractRole_withDifferentRoles_shouldExtractCorrectly() {
        // Test ADMIN role
        TokenResponse adminToken = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", jwtAdapter.extractRole(adminToken.getToken()));

        // Test SELLER role
        TokenResponse sellerToken = jwtAdapter.authenticate(credentials, "ROLE_SELLER");
        assertEquals("ROLE_SELLER", jwtAdapter.extractRole(sellerToken.getToken()));

        // Test OWNER role
        TokenResponse ownerToken = jwtAdapter.authenticate(credentials, "ROLE_OWNER");
        assertEquals("ROLE_OWNER", jwtAdapter.extractRole(ownerToken.getToken()));
    }

    @Test
    void extractUsername_withDifferentUsernames_shouldExtractCorrectly() {
        // Test first username
        credentials.setUsername("user1");
        TokenResponse token1 = jwtAdapter.authenticate(credentials, "ROLE_USER");
        assertEquals("user1", jwtAdapter.extractUsername(token1.getToken()));

        // Test second username
        credentials.setUsername("admin123");
        TokenResponse token2 = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");
        assertEquals("admin123", jwtAdapter.extractUsername(token2.getToken()));
    }

    @Test
    void authenticate_shouldGenerateUniqueTokensForSameUser() {
        // Act - Generate two tokens in quick succession
        TokenResponse token1 = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");
        
        try {
            // Small delay to ensure different issuedAt timestamps
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        TokenResponse token2 = jwtAdapter.authenticate(credentials, "ROLE_ADMIN");

        // Assert - Tokens should be different due to different issuedAt times
        assertNotEquals(token1.getToken(), token2.getToken());
    }

    @Test
    void validateToken_shouldAcceptRecentlyGeneratedToken() {
        // Arrange
        TokenResponse tokenResponse = jwtAdapter.authenticate(credentials, "ROLE_USER");

        // Act & Assert
        assertTrue(jwtAdapter.validateToken(tokenResponse.getToken()));
        assertEquals("testuser", jwtAdapter.extractUsername(tokenResponse.getToken()));
        assertEquals("ROLE_USER", jwtAdapter.extractRole(tokenResponse.getToken()));
    }
}
