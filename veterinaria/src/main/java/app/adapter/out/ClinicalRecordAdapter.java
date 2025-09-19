package app.adapter.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.ports.ClinicalRecordPort;
import app.infrastructure.persistence.entities.ClinicalRecordEntity;
import app.infrastructure.persistence.mapper.ClinicalRecordMapper;
import app.infrastructure.persistence.repository.ClinicalRecordRepository;

@Service
public class ClinicalRecordAdapter implements ClinicalRecordPort {
	
	@Autowired
	private ClinicalRecordRepository clinicalRecordRepository;

	@Override
	public void save(ClinicalRecord clinicalRecord) throws Exception {
		ClinicalRecordEntity entity = ClinicalRecordMapper.toEntity(clinicalRecord);
		clinicalRecordRepository.save(entity);
	}

	@Override
	public List<ClinicalRecord> findByPet(Pet pet) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
