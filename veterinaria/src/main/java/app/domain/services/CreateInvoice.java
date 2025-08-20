package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.InvoicePort;
import app.domain.ports.PetPort;

public class CreateInvoice {
	private PetPort petPort;
	private ClinicalOrderPort clinicalOrderPort;
	private InvoicePort invoicePort;
	
	public void create(Invoice invoice) throws Exception {
		Pet pet = petPort.findById(invoice.getPet());
		if(pet == null) {
			throw new Exception("la factura debe tener una mascota asociada");
		}
		if(invoice.isMedicine()) {
			ClinicalOrder clinicalOrder = clinicalOrderPort.findById(invoice.getOrder());
			if(clinicalOrder==null || pet.getId()!=clinicalOrder.getPet().getId()) {
				throw new Exception ("la venta de un medicamento requiere de una orden asociada");
			}
			invoice.setOrder(clinicalOrder);
		}
		invoice.setPet(pet);
		invoice.setOwner(pet.getOwner());
		invoicePort.save(invoice);
	}

}
