package app.infrastructure.persistence.mapper;

import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Spices;
import app.infrastructure.persistence.entities.PetEntity;
import app.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetMapperTest {

    private Pet pet;
    private PetEntity petEntity;
    private User owner;
    private UserEntity ownerEntity;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setId(1L);
        owner.setDocument(123456L);
        owner.setName("Owner Name");

        ownerEntity = new UserEntity();
        ownerEntity.setId(1L);
        ownerEntity.setDocument(123456L);
        ownerEntity.setName("Owner Name");

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
        petEntity.setOwner(ownerEntity);
        petEntity.setName("Fluffy");
        petEntity.setAge(5);
        petEntity.setWeight(10.5);
        petEntity.setSpecies("DOG");
        petEntity.setFeatures("Friendly");
        petEntity.setBreed("Labrador");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Act
        PetEntity result = PetMapper.toEntity(pet);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Fluffy", result.getName());
        assertEquals(5, result.getAge());
        assertEquals(10.5, result.getWeight());
        assertEquals("DOG", result.getSpecies());
        assertEquals("Friendly", result.getFeatures());
        assertEquals("Labrador", result.getBreed());
        assertNotNull(result.getOwner());
        assertEquals(123456L, result.getOwner().getDocument());
    }

    @Test
    void toEntity_withNullSpecies_shouldMapAsNull() {
        // Arrange
        pet.setSpices(null);

        // Act
        PetEntity result = PetMapper.toEntity(pet);

        // Assert
        assertNull(result.getSpecies());
    }

    @Test
    void toDomain_shouldMapAllFields() {
        // Act
        Pet result = PetMapper.toDomain(petEntity);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Fluffy", result.getName());
        assertEquals(5, result.getAge());
        assertEquals(10.5, result.getWeigth());
        assertEquals(Spices.DOG, result.getSpices());
        assertEquals("Friendly", result.getFeatures());
        assertEquals("Labrador", result.getBreed());
        assertNotNull(result.getOwner());
        assertEquals(123456L, result.getOwner().getDocument());
    }

    @Test
    void toDomain_withNullSpecies_shouldMapAsNull() {
        // Arrange
        petEntity.setSpecies(null);

        // Act
        Pet result = PetMapper.toDomain(petEntity);

        // Assert
        assertNull(result.getSpices());
    }

    @Test
    void toEntity_withAllSpices_shouldMapCorrectly() {
        // Test CAT
        pet.setSpices(Spices.CAT);
        assertEquals("CAT", PetMapper.toEntity(pet).getSpecies());

        // Test BIRD
        pet.setSpices(Spices.BIRD);
        assertEquals("BIRD", PetMapper.toEntity(pet).getSpecies());

        // Test RABBIT
        pet.setSpices(Spices.RABBIT);
        assertEquals("RABBIT", PetMapper.toEntity(pet).getSpecies());
    }

    @Test
    void toDomain_withAllSpices_shouldMapCorrectly() {
        // Test CAT
        petEntity.setSpecies("CAT");
        assertEquals(Spices.CAT, PetMapper.toDomain(petEntity).getSpices());

        // Test BIRD
        petEntity.setSpecies("BIRD");
        assertEquals(Spices.BIRD, PetMapper.toDomain(petEntity).getSpices());

        // Test RABBIT
        petEntity.setSpecies("RABBIT");
        assertEquals(Spices.RABBIT, PetMapper.toDomain(petEntity).getSpices());
    }
}
