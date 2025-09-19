package app.adapter.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.Pet;
import app.domain.ports.PetPort;
import app.infrastructure.persistence.entities.PetEntity;
import app.infrastructure.persistence.mapper.PetMapper;
import app.infrastructure.persistence.repository.PetRepository;


@Service
public class PetAdapter implements PetPort {
	
	@Autowired
	private PetRepository petRepository;

	@Override
	public void save(Pet pet) throws Exception {
		PetEntity petEntity = PetMapper.toEntity(pet);
		petRepository.save(petEntity);		
	}

	@Override
	public Pet findById(Pet pet) throws Exception {
		
		PetEntity petEntity = petRepository.findById(pet.getId());
		return PetMapper.toDomain(petEntity);
	}
	

}
