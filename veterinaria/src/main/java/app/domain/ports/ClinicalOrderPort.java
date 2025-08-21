package app.domain.ports;

import java.util.List;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;

public interface ClinicalOrderPort {

	public ClinicalOrder findById(ClinicalOrder clinicalOrder) throws Exception;
	public List<ClinicalOrder> findByPet(Pet pet)throws Exception;
	public void save(ClinicalOrder clinicalOrder) throws Exception;

}
