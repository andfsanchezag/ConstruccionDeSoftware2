package app.domain.services;

import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.ports.PetPort;
import app.domain.ports.UserPort;

public class CreatePet {
	
	private UserPort userPort;
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
