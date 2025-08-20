package app.domain.ports;

import java.util.List;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;

public interface ClinicalRecordPort {

	public void save(ClinicalRecord clinicalRecord) throws Exception;
	public List<ClinicalRecord> findByPet(Pet pet) throws Exception;

}
