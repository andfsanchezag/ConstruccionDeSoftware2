package app.domain.ports;

import app.domain.model.ClinicalRecord;

public interface ClinicalRecordPort {

	public void save(ClinicalRecord clinicalRecord) throws Exception;

}
