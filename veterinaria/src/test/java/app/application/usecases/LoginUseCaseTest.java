package app.application.usecases;

import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import app.domain.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private AuthCredentials credentials;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        credentials = new AuthCredentials();
        credentials.setUsername("testuser");
        credentials.setPassword("password123");

        tokenResponse = new TokenResponse();
        tokenResponse.setToken("jwt-token-xyz");
    }

    @Test
    void login_withValidCredentials_shouldReturnToken() throws Exception {
        // Arrange
        when(authenticationService.authenticate(credentials)).thenReturn(tokenResponse);

        // Act
        TokenResponse result = loginUseCase.login(credentials);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token-xyz", result.getToken());
        verify(authenticationService).authenticate(credentials);
    }

    @Test
    void login_whenAuthenticationFails_shouldPropagateException() throws Exception {
        // Arrange
        when(authenticationService.authenticate(credentials)).thenThrow(new Exception("Auth failed"));

        // Act & Assert
        assertThrows(Exception.class, () -> 
            loginUseCase.login(credentials)
        );
        verify(authenticationService).authenticate(credentials);
    }

    @Test
    void login_shouldPassCredentialsToAuthenticationService() throws Exception {
        // Arrange
        when(authenticationService.authenticate(credentials)).thenReturn(tokenResponse);

        // Act
        loginUseCase.login(credentials);

        // Assert
        verify(authenticationService).authenticate(same(credentials));
    }
}
