package app.adapter.out;

import java.util.List;

import org.springframework.stereotype.Service;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.ports.ClinicalOrderPort;


@Service
public class ClinicalOrderAdapter implements ClinicalOrderPort {

	@Override
	public ClinicalOrder findById(ClinicalOrder clinicalOrder) throws Exception {
		
		return null;
	}

	@Override
	public List<ClinicalOrder> findByPet(Pet pet) throws Exception {
		return null;
	}

	@Override
	public void save(ClinicalOrder clinicalOrder) throws Exception {
		
	}

}
