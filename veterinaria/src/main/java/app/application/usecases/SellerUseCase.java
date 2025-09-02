package app.application.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.services.CreateInvoice;
import app.domain.services.SearchClinicalOrderByPet;

@Service
public class SellerUseCase {
	@Autowired
	private CreateInvoice createInvoice;
	private SearchClinicalOrderByPet searchClinicalOrderByPet;

	public void CreateInvoice(Invoice invoice) throws Exception {
		createInvoice.create(invoice);
	}
	
	public List<ClinicalOrder> searchClinicalOrder(Pet pet) throws Exception{
		return searchClinicalOrderByPet.search(pet);
	}

}
