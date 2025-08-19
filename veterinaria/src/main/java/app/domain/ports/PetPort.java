package app.domain.ports;

import app.domain.model.Pet;

public interface PetPort {
	public void save(Pet pet) throws Exception;

	public Pet findById(Pet pet) throws Exception;
}
