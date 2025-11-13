package app.adapter.in.builder;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapter.in.validators.ClinicalOrderValidator;
import app.adapter.in.validators.ClinicalRecordValidator;
import app.adapter.in.validators.PetValidator;
import app.adapter.in.validators.UserValidator;
import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;

@Component
public class ClinicalRecordBuilder {
	
	@Autowired
	private ClinicalRecordValidator clinicalRecordValidator;
	@Autowired
	private UserValidator uservalidator;
	@Autowired
	private PetValidator petValidator;
	@Autowired
	private ClinicalOrderValidator clinialOrderValidator;
	
	
	public ClinicalRecord create(String document,String petId,String orderId)throws Exception{
		ClinicalRecord clinicalRecord = new ClinicalRecord();
		User veterinarian = new User();
		Pet pet = new Pet();
		ClinicalOrder order = new ClinicalOrder();
		veterinarian.setDocument(uservalidator.documentValidator(document));
		pet.setId(petValidator.idValidator(petId));
		order.setId(clinialOrderValidator.idValidator(orderId));
		clinicalRecord.setVeterinarian(veterinarian);
		clinicalRecord.setPet(pet);
		clinicalRecord.setClinicalOrder(order);
		clinicalRecord.setDate(new Date(System.currentTimeMillis()));
		clinicalRecord.setStatus(true);
		return clinicalRecord;
	}
}
