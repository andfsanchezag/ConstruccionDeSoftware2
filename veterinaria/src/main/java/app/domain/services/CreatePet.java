package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.ports.PetPort;
import app.domain.ports.UserPort;

@Service
public class CreatePet {
	@Autowired
	private UserPort userPort;
	@Autowired
	private PetPort petPort;
	
	
	public void create(Pet pet)throws Exception {
		User owner = userPort.findByDocument(pet.getOwner());
		if(owner== null) {
			throw new Exception("la mascota debe tener un dueño valido");
		}
		pet.setOwner(owner);
		petPort.save(pet);
	}

}
