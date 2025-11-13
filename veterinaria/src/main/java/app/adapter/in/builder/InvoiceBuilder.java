package app.adapter.in.builder;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapter.in.validators.InvoiceValidator;
import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.model.User;


@Component
public class InvoiceBuilder {
	@Autowired
	private InvoiceValidator invoiceValidator;

	public Invoice build(String petId, String ownerDocument, String productAmount, String productName,
			String isMedicine, String orderId) throws Exception {
		Invoice invoice = new Invoice();
		Pet pet = new Pet();
		pet.setId(invoiceValidator.petIdValidator(petId));
		invoice.setPet(pet);
		User owner = new User();
		owner.setDocument(invoiceValidator.ownerDocumentValidator(ownerDocument));
		invoice.setOwner(owner);
		invoice.setProductAmount(invoiceValidator.amountValidator(productAmount));
		invoice.setProductName(invoiceValidator.productNameValidator(productName));
		invoice.setDate(new Date(System.currentTimeMillis()));
		invoice.setMedicine(invoiceValidator.isMedicineValidator(isMedicine));
		if (invoice.isMedicine()) {
			ClinicalOrder clinicalOrder = new ClinicalOrder();
			clinicalOrder.setId(invoiceValidator.orderIdValidator(orderId));
			invoice.setOrder(clinicalOrder);
		}
		return invoice;
	}
}
