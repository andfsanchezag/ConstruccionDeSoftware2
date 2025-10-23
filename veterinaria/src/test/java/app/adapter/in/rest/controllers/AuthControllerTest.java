package app.adapter.in.rest.controllers;

import app.adapter.rest.mapper.AuthRestMapper;
import app.adapter.rest.request.AuthRequest;
import app.adapter.rest.response.TokenResponseDto;
import app.application.usecases.LoginUseCase;
import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private AuthRestMapper authRestMapper;

    @InjectMocks
    private AuthController authController;

    private AuthRequest request;
    private AuthCredentials credentials;
    private TokenResponse tokenResponse;
    private TokenResponseDto tokenResponseDto;

    @BeforeEach
    void setUp() {
        request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("pass123");

        credentials = new AuthCredentials();
        credentials.setUsername("testuser");
        credentials.setPassword("pass123");

        tokenResponse = new TokenResponse();
        tokenResponse.setToken("jwt-token-xyz");

        tokenResponseDto = new TokenResponseDto("jwt-token-xyz");
    }

    @Test
    void login_withValidCredentials_shouldReturnOkWithToken() throws Exception {
        // Arrange
        when(authRestMapper.toDomain(request)).thenReturn(credentials);
        when(loginUseCase.login(credentials)).thenReturn(tokenResponse);
        when(authRestMapper.toResponse(tokenResponse)).thenReturn(tokenResponseDto);

        // Act
        ResponseEntity<TokenResponseDto> response = authController.login(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token-xyz", response.getBody().getToken());
        
        verify(authRestMapper).toDomain(request);
        verify(loginUseCase).login(credentials);
        verify(authRestMapper).toResponse(tokenResponse);
    }

    @Test
    void login_whenLoginFails_shouldPropagateException() throws Exception {
        // Arrange
        when(authRestMapper.toDomain(request)).thenReturn(credentials);
        when(loginUseCase.login(credentials)).thenThrow(new Exception("Login failed"));

        // Act & Assert
        assertThrows(Exception.class, () -> 
            authController.login(request)
        );
    }

    @Test
    void login_shouldCallMapperInCorrectOrder() throws Exception {
        // Arrange
        when(authRestMapper.toDomain(request)).thenReturn(credentials);
        when(loginUseCase.login(credentials)).thenReturn(tokenResponse);
        when(authRestMapper.toResponse(tokenResponse)).thenReturn(tokenResponseDto);

        // Act
        authController.login(request);

        // Assert
        var inOrder = inOrder(authRestMapper, loginUseCase);
        inOrder.verify(authRestMapper).toDomain(request);
        inOrder.verify(loginUseCase).login(credentials);
        inOrder.verify(authRestMapper).toResponse(tokenResponse);
    }
}
