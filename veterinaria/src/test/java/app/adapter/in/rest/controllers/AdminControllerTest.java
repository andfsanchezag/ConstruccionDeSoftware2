package app.adapter.in.rest.controllers;

import app.adapter.rest.mapper.UserRestMapper;
import app.adapter.rest.request.CreateUserRequest;
import app.adapter.rest.response.UserResponse;
import app.application.usecases.AdminUseCase;
import app.domain.model.User;
import app.domain.model.emuns.Role;
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
class AdminControllerTest {

    @Mock
    private AdminUseCase adminUseCase;

    @Mock
    private UserRestMapper userRestMapper;

    @InjectMocks
    private AdminController adminController;

    private CreateUserRequest request;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        request = new CreateUserRequest();
        request.setName("John Doe");
        request.setDocument("123456");
        request.setAge("30");
        request.setUserName("jdoe");
        request.setPassword("pass123");

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setDocument(123456L);

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("John Doe");
        userResponse.setDocument(123456L);
    }

    @Test
    void createVeterinarian_shouldReturnCreatedWithUser() throws Exception {
        // Arrange
        user.setRole(Role.VETERINARIAN);
        userResponse.setRole("VETERINARIAN");
        when(userRestMapper.toDomain(request)).thenReturn(user);
        when(userRestMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        ResponseEntity<UserResponse> response = adminController.createVeterinarian(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("VETERINARIAN", response.getBody().getRole());
        
        verify(userRestMapper).toDomain(request);
        verify(adminUseCase).createVeterinarian(user);
        verify(userRestMapper).toResponse(user);
    }

    @Test
    void createSeller_shouldReturnCreatedWithUser() throws Exception {
        // Arrange
        user.setRole(Role.SELLER);
        userResponse.setRole("SELLER");
        when(userRestMapper.toDomain(request)).thenReturn(user);
        when(userRestMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        ResponseEntity<UserResponse> response = adminController.createSeller(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SELLER", response.getBody().getRole());
        
        verify(userRestMapper).toDomain(request);
        verify(adminUseCase).createSeller(user);
        verify(userRestMapper).toResponse(user);
    }

    @Test
    void createVeterinarian_whenUseCaseThrows_shouldPropagateException() throws Exception {
        // Arrange
        when(userRestMapper.toDomain(request)).thenReturn(user);
        doThrow(new Exception("Creation failed")).when(adminUseCase).createVeterinarian(user);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            adminController.createVeterinarian(request)
        );
    }

    @Test
    void createSeller_whenUseCaseThrows_shouldPropagateException() throws Exception {
        // Arrange
        when(userRestMapper.toDomain(request)).thenReturn(user);
        doThrow(new Exception("Creation failed")).when(adminUseCase).createSeller(user);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            adminController.createSeller(request)
        );
    }

    @Test
    void createVeterinarian_shouldCallServicesInCorrectOrder() throws Exception {
        // Arrange
        when(userRestMapper.toDomain(request)).thenReturn(user);
        when(userRestMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        adminController.createVeterinarian(request);

        // Assert
        var inOrder = inOrder(userRestMapper, adminUseCase);
        inOrder.verify(userRestMapper).toDomain(request);
        inOrder.verify(adminUseCase).createVeterinarian(user);
        inOrder.verify(userRestMapper).toResponse(user);
    }
}
