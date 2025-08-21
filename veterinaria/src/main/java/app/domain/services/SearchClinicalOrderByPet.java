package app.domain.services;

import java.util.List;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.PetPort;

public class SearchClinicalOrderByPet {

	private PetPort petPort;
	private ClinicalOrderPort clinicalOrderPort;

	public List<ClinicalOrder> search(Pet pet) throws Exception {
		pet = petPort.findById(pet);
		if (pet == null) {
			throw new Exception("debe consultar ordenes de una mascota registrada");
		}
		return clinicalOrderPort.findByPet(pet);

	}

}
