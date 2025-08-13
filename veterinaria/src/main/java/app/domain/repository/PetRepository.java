package veterinaria.domain.repository;



import java.util.List;
import java.util.Optional;

import veterinaria.domain.model.Pet;

public interface PetRepository {
    Optional<Pet> findById(String petId);
    List<Pet> findByOwnerDocumentId(String ownerDocumentId);
    void save(Pet pet);
}
