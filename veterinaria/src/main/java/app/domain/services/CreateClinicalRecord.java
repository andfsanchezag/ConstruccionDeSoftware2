package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.PetPort;
import app.domain.ports.UserPort;

@Service
public class CreateClinicalRecord {
	
	@Autowired
	private UserPort userPort;
	@Autowired
	private PetPort petPort;
	@Autowired
	private ClinicalOrderPort clinicalOrderPort;
	@Autowired
	private ClinicalRecordPort clinicalRecordPort;
	
	public void create(ClinicalRecord clinicalRecord) throws Exception{
		Pet pet = petPort.findById(clinicalRecord.getPet());
		if(pet == null) {
			throw new Exception("la historia debe tener una mascota valida");
		}
		User veterinarian = userPort.findByDocument(clinicalRecord.getVeterinarian());
		if(veterinarian==null || !veterinarian.getRole().equals(Role.VETERINARIAN)) {
			throw new Exception("la historia clinica debe ser registrada por un veterinario valido");
		}
		ClinicalOrder clinicalOrder = clinicalOrderPort.findById(clinicalRecord.getClinicalOrder());
		if(clinicalOrder==null) {
			throw new Exception("la historia clinica debe tener una orden valida asociada");
		}
		clinicalRecord.setPet(pet);
		clinicalRecord.setVeterinarian(veterinarian);
		clinicalRecord.setClinicalOrder(clinicalOrder);
		clinicalRecordPort.save(clinicalRecord);
		
	}

}
