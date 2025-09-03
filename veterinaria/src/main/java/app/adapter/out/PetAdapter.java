package app.adapter.out;

import org.springframework.stereotype.Service;

import app.domain.model.Pet;
import app.domain.ports.PetPort;


@Service
public class PetAdapter implements PetPort {

	@Override
	public void save(Pet pet) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pet findById(Pet pet) throws Exception {
		if(pet.getId()==1) {
			return pet;
		}
		return null;
	}
	

}
