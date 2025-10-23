package app.adapter.rest.mapper;

import app.adapter.in.builder.PetBuilder;
import app.adapter.rest.request.PetRequest;
import app.adapter.rest.response.PetResponse;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Spices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetRestMapperTest {

    @Mock
    private PetBuilder petBuilder;

    @InjectMocks
    private PetRestMapper petRestMapper;

    private PetRequest request;
    private Pet pet;
    private User owner;

    @BeforeEach
    void setUp() {
        request = new PetRequest();
        request.setOwnerDocument("123456");
        request.setName("Rex");
        request.setAge("5");
        request.setWeigth("25.5");
        request.setSpices("DOG");
        request.setFeatures("Friendly");
        request.setBreed("Labrador");

        owner = new User();
        owner.setDocument(123456L);

        pet = new Pet();
        pet.setId(10L);
        pet.setName("Rex");
        pet.setAge(5);
        pet.setWeigth(25.5);
        pet.setSpices(Spices.DOG);
        pet.setFeatures("Friendly");
        pet.setBreed("Labrador");
        pet.setOwner(owner);
    }

    @Test
    void toDomain_shouldCallBuilderWithCorrectParams() throws Exception {
        // Arrange
        when(petBuilder.builder(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(pet);

        // Act
        Pet result = petRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        verify(petBuilder).builder("123456", "Rex", "5", "25.5", "DOG", "Friendly", "Labrador");
    }

    @Test
    void toDomain_whenBuilderThrows_shouldPropagateException() throws Exception {
        // Arrange
        when(petBuilder.builder(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenThrow(new Exception("Build failed"));

        // Act & Assert
        assertThrows(Exception.class, () -> 
            petRestMapper.toDomain(request)
        );
    }

    @Test
    void toResponse_shouldMapAllFields() {
        // Act
        PetResponse result = petRestMapper.toResponse(pet);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Rex", result.getName());
        assertEquals(5, result.getAge());
        assertEquals(25.5, result.getWeigth(), 0.001);
        assertEquals("DOG", result.getSpices());
        assertEquals("Friendly", result.getFeatures());
        assertEquals("Labrador", result.getBreed());
        assertEquals(123456L, result.getOwnerDocument());
    }

    @Test
    void toResponse_withNullSpices_shouldMapSpicesAsNull() {
        // Arrange
        pet.setSpices(null);

        // Act
        PetResponse result = petRestMapper.toResponse(pet);

        // Assert
        assertNull(result.getSpices());
    }

    @Test
    void toResponse_withNullOwner_shouldMapOwnerDocumentAsZero() {
        // Arrange
        pet.setOwner(null);

        // Act
        PetResponse result = petRestMapper.toResponse(pet);

        // Assert
        assertEquals(0L, result.getOwnerDocument());
    }

    @Test
    void toResponse_withAllSpicesTypes_shouldMapCorrectly() {
        for (Spices spice : Spices.values()) {
            pet.setSpices(spice);
            PetResponse result = petRestMapper.toResponse(pet);
            assertEquals(String.valueOf(spice), result.getSpices());
        }
    }
}
