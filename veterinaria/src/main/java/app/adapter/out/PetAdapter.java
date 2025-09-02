package app.adapter.out;

import org.springframework.stereotype.Service;

import app.domain.model.Pet;
import app.domain.ports.PetPort;

@Service
public class PetAdapter implements PetPort {

	@Override
	public void save(Pet pet) throws Exception {
		System.out.println("se ha creado la mascota");
		
	}

	@Override
	public Pet findById(Pet pet) throws Exception {
		if(pet.getId()==1) {
			return new Pet();
		}
		return null;
	}

}
