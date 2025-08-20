package app.domain.ports;

import app.domain.model.ClinicalOrder;

public interface ClinicalOrderPort {

	public ClinicalOrder findById(ClinicalOrder clinicalOrder) throws Exception;
	public void save(ClinicalOrder clinicalOrder) throws Exception;

}
