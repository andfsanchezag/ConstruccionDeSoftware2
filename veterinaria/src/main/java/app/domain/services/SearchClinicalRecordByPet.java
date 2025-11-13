package app.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.PetPort;

@Service
public class SearchClinicalRecordByPet {
    
	@Autowired
	private PetPort petPort;
	@Autowired
	private ClinicalRecordPort clinicalRecordPort;
    
	public List<ClinicalRecord> search(Pet pet) throws Exception{
		pet = petPort.findById(pet);
		if(pet == null) {
			throw new Exception("no existe la mascota buscada");
		}
		return clinicalRecordPort.findByPet(pet);
        
	}

}
