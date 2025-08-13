package app.domain.repository;



import java.util.List;
import java.util.Optional;

import app.domain.model.Pet;

public interface PetRepository {
    Optional<Pet> findById(String petId);
    List<Pet> findByOwnerDocumentId(String ownerDocumentId);
    void save(Pet pet);
}
