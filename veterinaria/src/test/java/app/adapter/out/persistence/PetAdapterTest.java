package app.adapter.out.persistence;

import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Spices;
import app.infrastructure.persistence.entities.PetEntity;
import app.infrastructure.persistence.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetAdapterTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetAdapter petAdapter;

    private Pet pet;
    private PetEntity petEntity;
    private User owner;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setId(1L);
        owner.setDocument(123456L);
        owner.setName("Owner Name");

        pet = new Pet();
        pet.setId(10L);
        pet.setOwner(owner);
        pet.setName("Fluffy");
        pet.setAge(5);
        pet.setWeigth(10.5);
        pet.setSpices(Spices.DOG);
        pet.setFeatures("Friendly");
        pet.setBreed("Labrador");

        petEntity = new PetEntity();
        petEntity.setId(10L);
        petEntity.setName("Fluffy");
        petEntity.setAge(5);
        petEntity.setWeight(10.5);
        petEntity.setSpecies("DOG");
    }

    @Test
    void save_shouldCallRepository() throws Exception {
        // Arrange
        when(petRepository.save(any(PetEntity.class))).thenReturn(petEntity);

        // Act
        petAdapter.save(pet);

        // Assert
        verify(petRepository).save(any(PetEntity.class));
    }

    @Test
    void save_shouldConvertToEntityBeforeSaving() throws Exception {
        // Arrange
        when(petRepository.save(any(PetEntity.class))).thenReturn(petEntity);

        // Act
        petAdapter.save(pet);

        // Assert
        verify(petRepository).save(argThat(entity ->
            entity.getId() == 10L &&
            entity.getName().equals("Fluffy") &&
            entity.getSpecies().equals("DOG")
        ));
    }

    @Test
    void findById_shouldReturnPet() throws Exception {
        // Arrange
        when(petRepository.findById(10L)).thenReturn(petEntity);

        // Act
        Pet result = petAdapter.findById(pet);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Fluffy", result.getName());
        verify(petRepository).findById(10L);
    }

    @Test
    void findById_whenNotFound_shouldReturnNull() throws Exception {
        // Arrange
        when(petRepository.findById(10L)).thenReturn(null);

        // Act
        Pet result = petAdapter.findById(pet);

        // Assert
        assertNull(result);
        verify(petRepository).findById(10L);
    }

    @Test
    void findById_shouldConvertEntityToDomain() throws Exception {
        // Arrange
        petEntity.setSpecies("CAT");
        when(petRepository.findById(10L)).thenReturn(petEntity);

        // Act
        Pet result = petAdapter.findById(pet);

        // Assert
        assertNotNull(result);
        assertEquals(Spices.CAT, result.getSpices());
    }
}
