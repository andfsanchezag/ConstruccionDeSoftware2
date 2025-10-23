package app.domain.services;

import app.application.exceptions.BusinessException;
import app.domain.model.User;
import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import app.domain.ports.AuthenticationPort;
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
class AuthenticationServiceTest {

    @Mock
    private AuthenticationPort authenticationPort;

    @Mock
    private UserPort userPort;

    @InjectMocks
    private AuthenticationService authenticationService;

    private AuthCredentials credentials;
    private User testUser;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        credentials = new AuthCredentials();
        credentials.setUsername("testuser");
        credentials.setPassword("password123");

        testUser = new User();
        testUser.setUserName("testuser");
        testUser.setPassword("password123");
        testUser.setDocument(123456L);

        tokenResponse = new TokenResponse();
        tokenResponse.setToken("jwt-token-123");
    }

    @Test
    void authenticate_withValidCredentials_shouldReturnToken() throws Exception {
        // Arrange
        when(userPort.findByUserName(any(User.class))).thenReturn(testUser);
        when(authenticationPort.authenticate(any(AuthCredentials.class), anyString())).thenReturn(tokenResponse);

        // Act
        TokenResponse result = authenticationService.authenticate(credentials);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token-123", result.getToken());
        verify(userPort).findByUserName(any(User.class));
        verify(authenticationPort).authenticate(any(AuthCredentials.class), anyString());
    }

    @Test
    void authenticate_withNonExistentUser_shouldThrowBusinessException() throws Exception {
        // Arrange
        when(userPort.findByUserName(any(User.class))).thenReturn(null);

        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class, () -> 
            authenticationService.authenticate(credentials)
        );
        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
        verify(userPort).findByUserName(any(User.class));
        verify(authenticationPort, never()).authenticate(any(), any());
    }

    @Test
    void authenticate_withWrongPassword_shouldThrowBusinessException() throws Exception {
        // Arrange
        credentials.setPassword("wrongpassword");
        when(userPort.findByUserName(any(User.class))).thenReturn(testUser);

        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class, () -> 
            authenticationService.authenticate(credentials)
        );
        assertTrue(ex.getMessage().contains("Contraseña incorrecta"));
        verify(userPort).findByUserName(any(User.class));
        verify(authenticationPort, never()).authenticate(any(), any());
    }

    @Test
    void authenticate_withCorrectPassword_shouldCallAuthenticationPort() throws Exception {
        // Arrange
        when(userPort.findByUserName(any(User.class))).thenReturn(testUser);
        when(authenticationPort.authenticate(any(AuthCredentials.class), anyString())).thenReturn(tokenResponse);

        // Act
        authenticationService.authenticate(credentials);

        // Assert
        verify(authenticationPort).authenticate(credentials, String.valueOf(testUser.getRole()));
    }

    @Test
    void authenticate_shouldPassUserRoleToAuthenticationPort() throws Exception {
        // Arrange
        when(userPort.findByUserName(any(User.class))).thenReturn(testUser);
        when(authenticationPort.authenticate(any(AuthCredentials.class), eq("null"))).thenReturn(tokenResponse);

        // Act
        authenticationService.authenticate(credentials);

        // Assert
        verify(authenticationPort).authenticate(any(AuthCredentials.class), eq("null"));
    }
}
