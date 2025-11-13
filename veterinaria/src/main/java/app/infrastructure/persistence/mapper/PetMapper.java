package app.infrastructure.persistence.mapper;

import app.domain.model.Pet;
import app.domain.model.emuns.Spices; // (corrígelo a Species en tu dominio)
import app.infrastructure.persistence.entities.PetEntity;

public class PetMapper {

    // De dominio → entidad
    public static PetEntity toEntity(Pet pet) {
        if (pet == null) return null;
        PetEntity entity = new PetEntity();
        entity.setId(pet.getId());
        entity.setOwner(UserMapper.toEntity(pet.getOwner()));
        entity.setName(pet.getName());
        entity.setAge(pet.getAge());
    entity.setWeight(pet.getWeigth()); // recuerda corregir en dominio a weight
    entity.setSpecies(pet.getSpices() != null ? String.valueOf(pet.getSpices()) : null);
        entity.setFeatures(pet.getFeatures());
        entity.setBreed(pet.getBreed());
        return entity;
    }

    // De entidad → dominio
    public static Pet toDomain(PetEntity entity) {
        if (entity == null) return null;
        Pet pet = new Pet();
        pet.setId(entity.getId());
        pet.setOwner(UserMapper.toDomain(entity.getOwner()));
        pet.setName(entity.getName());
        pet.setAge(entity.getAge());
        pet.setWeigth(entity.getWeight()); // recuerda corregir en dominio a weight
        pet.setSpices(fromString(entity.getSpecies()));
        pet.setFeatures(entity.getFeatures());
        pet.setBreed(entity.getBreed());
        return pet;
    }

    private static Spices fromString(String species) {
        if (species == null) return null;
        try {
            return Spices.valueOf(species.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null; // valor no reconocido
        }
    }
}
