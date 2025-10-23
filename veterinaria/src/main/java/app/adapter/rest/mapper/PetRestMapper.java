package app.adapter.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapter.in.builder.PetBuilder;
import app.adapter.rest.request.PetRequest;
import app.adapter.rest.response.PetResponse;
import app.domain.model.Pet;

@Component
public class PetRestMapper {
    @Autowired
    private PetBuilder petBuilder;

    public Pet toDomain(PetRequest req) throws Exception {
        return petBuilder.builder(
            req.getOwnerDocument(),
            req.getName(),
            req.getAge(),
            req.getWeigth(),
            req.getSpices(),
            req.getFeatures(),
            req.getBreed()
        );
    }

    public PetResponse toResponse(Pet pet) {
        PetResponse res = new PetResponse();
        res.setId(pet.getId());
        res.setName(pet.getName());
        res.setAge(pet.getAge());
        res.setWeigth(pet.getWeigth());
        res.setSpices(pet.getSpices() != null ? String.valueOf(pet.getSpices()) : null);
        res.setFeatures(pet.getFeatures());
        res.setBreed(pet.getBreed());
        res.setOwnerDocument(pet.getOwner() != null ? pet.getOwner().getDocument() : 0);
        return res;
    }
}
