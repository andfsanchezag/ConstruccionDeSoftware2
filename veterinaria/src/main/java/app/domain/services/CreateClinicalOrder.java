package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.PetPort;
import app.domain.ports.UserPort;

public class CreateClinicalOrder {
	private UserPort userPort;
	private PetPort petPort;
	private ClinicalOrderPort clinicalOrderPort;

	public void create(ClinicalOrder clinicalOrder) throws Exception {
		User veterinarian = userPort.findByDocument(clinicalOrder.getVeterinarian());
		if (veterinarian == null || !veterinarian.getRole().equals(Role.VETERINARIAN)) {
			throw new Exception("Las ordenes solo las pueden crear veterinarios");
		}
		Pet pet = petPort.findById(clinicalOrder.getPet());
		if (pet == null) {
			throw new Exception("las ordenes se deben aplicar a mascotas registradas");
		}
		
		clinicalOrder.setPet(pet);
		clinicalOrder.setOwner(pet.getOwner());
		clinicalOrder.setVeterinarian(veterinarian);
		
		clinicalOrderPort.save(clinicalOrder);
	}
}
