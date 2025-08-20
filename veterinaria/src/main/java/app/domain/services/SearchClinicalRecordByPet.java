package app.domain.services;

import java.util.List;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.PetPort;

public class SearchClinicalRecordByPet {
	
	private PetPort petPort;
	private ClinicalRecordPort clinicalRecordPort;
	
	public List<ClinicalRecord> search(Pet pet) throws Exception{
		pet = petPort.findById(pet);
		if(pet == null) {
			throw new Exception("no existe la mascota buscada");
		}
		return clinicalRecordPort.findByPet(pet);
		
	}

}
