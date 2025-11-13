package app.adapter.rest.mapper;

import app.adapter.in.builder.ClinicalOrderBuilder;
import app.adapter.rest.request.ClinicalOrderRequest;
import app.adapter.rest.response.ClinicalOrderResponse;
import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClinicalOrderRestMapperTest {

    @Mock
    private ClinicalOrderBuilder clinicalOrderBuilder;

    @InjectMocks
    private ClinicalOrderRestMapper clinicalOrderRestMapper;

    private ClinicalOrderRequest request;
    private ClinicalOrder order;

    @BeforeEach
    void setUp() {
        request = new ClinicalOrderRequest();
        request.setVeterinarianDocument("123456");
        request.setPetId("10");
        request.setMedicine("Antibiotic");
        request.setDoce("500mg");

        Pet pet = new Pet();
        pet.setId(10L);
        
        User owner = new User();
        owner.setDocument(222222L);
        
        User vet = new User();
        vet.setDocument(123456L);

        order = new ClinicalOrder();
        order.setId(100L);
        order.setPet(pet);
        order.setOwner(owner);
        order.setVeterinarian(vet);
        order.setMedicine("Antibiotic");
        order.setDoce("500mg");
        order.setDate(new Date(System.currentTimeMillis()));
    }

    @Test
    void toDomain_shouldCallBuilder() throws Exception {
        // Arrange
        when(clinicalOrderBuilder.builder(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(order);

        // Act
        ClinicalOrder result = clinicalOrderRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        verify(clinicalOrderBuilder).builder("123456", "10", "Antibiotic", "500mg");
    }

    @Test
    void toResponse_shouldMapAllFields() {
        // Act
        ClinicalOrderResponse result = clinicalOrderRestMapper.toResponse(order);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(10L, result.getPetId());
        assertEquals(222222L, result.getOwnerDocument());
        assertEquals(123456L, result.getVeterinarianDocument());
        assertEquals("Antibiotic", result.getMedicine());
        assertEquals("500mg", result.getDoce());
        assertNotNull(result.getDate());
    }

    @Test
    void toResponse_withNullPet_shouldMapPetIdAsZero() {
        // Arrange
        order.setPet(null);

        // Act
        ClinicalOrderResponse result = clinicalOrderRestMapper.toResponse(order);

        // Assert
        assertEquals(0L, result.getPetId());
    }

    @Test
    void toResponse_withNullOwner_shouldMapOwnerDocumentAsZero() {
        // Arrange
        order.setOwner(null);

        // Act
        ClinicalOrderResponse result = clinicalOrderRestMapper.toResponse(order);

        // Assert
        assertEquals(0L, result.getOwnerDocument());
    }

    @Test
    void toResponse_withNullVeterinarian_shouldMapVetDocumentAsZero() {
        // Arrange
        order.setVeterinarian(null);

        // Act
        ClinicalOrderResponse result = clinicalOrderRestMapper.toResponse(order);

        // Assert
        assertEquals(0L, result.getVeterinarianDocument());
    }
}
