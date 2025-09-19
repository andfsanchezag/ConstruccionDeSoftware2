package app.infrastructure.persistence.mapper;

import app.domain.model.Pet;
import app.domain.model.emuns.Spices; // (corrígelo a Species en tu dominio)
import app.infrastructure.persistence.entities.PetEntity;

public class PetMapper {

    // De dominio → entidad
    public static PetEntity toEntity(Pet pet) {
        PetEntity entity = new PetEntity();
        entity.setId(pet.getId());
        entity.setOwner(UserMapper.toEntity(pet.getOwner()));
        entity.setName(pet.getName());
        entity.setAge(pet.getAge());
        entity.setWeight(pet.getWeigth()); // recuerda corregir en dominio a weight
        entity.setSpecies(pet.getSpices() != null ? pet.getSpices().name() : null);
        entity.setFeatures(pet.getFeatures());
        entity.setBreed(pet.getBreed());
        return entity;
    }

    // De entidad → dominio
    public static Pet toDomain(PetEntity entity) {
        Pet pet = new Pet();
        pet.setId(entity.getId());
        pet.setOwner(UserMapper.toDomain(entity.getOwner()));
        pet.setName(entity.getName());
        pet.setAge(entity.getAge());
        pet.setWeigth(entity.getWeight()); // recuerda corregir en dominio a weight
        pet.setSpices(entity.getSpecies() != null ? Spices.valueOf(entity.getSpecies()) : null);
        pet.setFeatures(entity.getFeatures());
        pet.setBreed(entity.getBreed());
        return pet;
    }
}
