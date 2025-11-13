package app.adapter.in.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapter.in.validators.ClinicalOrderValidator;
import app.adapter.in.validators.PetValidator;
import app.adapter.in.validators.UserValidator;
import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.model.User;

@Component
public class ClinicalOrderBuilder {
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private PetValidator petValidator;
	@Autowired
	private ClinicalOrderValidator clinicalOrderValidator;
	
	public ClinicalOrder builder(String document,String id, String medicine,String doce) throws Exception {
		User veterinarian = new User();
		Pet pet = new Pet();
		ClinicalOrder clinicalOrder = new ClinicalOrder();
		veterinarian.setDocument(userValidator.documentValidator(document));
		pet.setId(petValidator.idValidator(id));
		clinicalOrder.setVeterinarian(veterinarian);
		clinicalOrder.setPet(pet);
		clinicalOrder.setMedicine(clinicalOrderValidator.medicineValidator(medicine));
		clinicalOrder.setDoce(clinicalOrderValidator.doceValidator(doce));
		return clinicalOrder;
	}

}
