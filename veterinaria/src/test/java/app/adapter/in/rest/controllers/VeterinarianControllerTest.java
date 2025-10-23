package app.adapter.in.rest.controllers;

import app.adapter.rest.mapper.*;
import app.adapter.rest.request.*;
import app.adapter.rest.response.*;
import app.application.usecases.VeterinarianUseCase;
import app.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeterinarianControllerTest {

    @Mock
    private VeterinarianUseCase veterinarianUseCase;
    @Mock
    private UserRestMapper userRestMapper;
    @Mock
    private PetRestMapper petRestMapper;
    @Mock
    private ClinicalOrderRestMapper clinicalOrderRestMapper;
    @Mock
    private ClinicalRecordRestMapper clinicalRecordRestMapper;

    @InjectMocks
    private VeterinarianController veterinarianController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createOwner_shouldReturnCreated() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        User user = new User();
        UserResponse response = new UserResponse();
        when(userRestMapper.toDomain(request)).thenReturn(user);
        when(userRestMapper.toResponse(user)).thenReturn(response);

        // Act
        ResponseEntity<UserResponse> result = veterinarianController.createOwner(request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(veterinarianUseCase).CreateOwner(user);
    }

    @Test
    void createPet_shouldReturnCreated() throws Exception {
        // Arrange
        PetRequest request = new PetRequest();
        Pet pet = new Pet();
        PetResponse response = new PetResponse();
        when(petRestMapper.toDomain(request)).thenReturn(pet);
        when(petRestMapper.toResponse(pet)).thenReturn(response);

        // Act
        ResponseEntity<PetResponse> result = veterinarianController.createPet(request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(veterinarianUseCase).CreatePet(pet);
    }

    @Test
    void createOrder_shouldReturnCreated() throws Exception {
        // Arrange
        ClinicalOrderRequest request = new ClinicalOrderRequest();
        ClinicalOrder order = new ClinicalOrder();
        ClinicalOrderResponse response = new ClinicalOrderResponse();
        when(clinicalOrderRestMapper.toDomain(request)).thenReturn(order);
        when(clinicalOrderRestMapper.toResponse(order)).thenReturn(response);

        // Act
        ResponseEntity<ClinicalOrderResponse> result = veterinarianController.createOrder(request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(veterinarianUseCase).createOrder(order);
    }

    @Test
    void searchOrders_shouldReturnOkWithList() throws Exception {
        // Arrange
        ClinicalOrder order = new ClinicalOrder();
        ClinicalOrderResponse orderResp = new ClinicalOrderResponse();
        when(veterinarianUseCase.searchOrders(any(Pet.class))).thenReturn(Arrays.asList(order));
        when(clinicalOrderRestMapper.toResponse(order)).thenReturn(orderResp);

        // Act
        ResponseEntity<List<ClinicalOrderResponse>> result = veterinarianController.searchOrders("10");

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void createClinicalRecord_shouldReturnCreated() throws Exception {
        // Arrange
        ClinicalRecordRequest request = new ClinicalRecordRequest();
        ClinicalRecord record = new ClinicalRecord();
        ClinicalRecordResponse response = new ClinicalRecordResponse();
        when(clinicalRecordRestMapper.toDomain(request)).thenReturn(record);
        when(clinicalRecordRestMapper.toResponse(record)).thenReturn(response);

        // Act
        ResponseEntity<ClinicalRecordResponse> result = veterinarianController.createClinicalRecord(request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(veterinarianUseCase).createClinicalRecord(record);
    }

    @Test
    void createOwner_whenUseCaseThrows_shouldPropagateException() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        User user = new User();
        when(userRestMapper.toDomain(request)).thenReturn(user);
        doThrow(new Exception("Failed")).when(veterinarianUseCase).CreateOwner(user);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            veterinarianController.createOwner(request)
        );
    }
}
