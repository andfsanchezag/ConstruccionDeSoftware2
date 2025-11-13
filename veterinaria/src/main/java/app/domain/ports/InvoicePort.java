package app.domain.ports;

import app.domain.model.Invoice;

public interface InvoicePort {

	public void save(Invoice invoice)throws Exception;

}
