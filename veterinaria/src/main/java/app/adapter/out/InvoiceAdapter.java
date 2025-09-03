package app.adapter.out;

import org.springframework.stereotype.Service;

import app.domain.model.Invoice;
import app.domain.ports.InvoicePort;
@Service
public class InvoiceAdapter implements InvoicePort {

	@Override
	public void save(Invoice invoice) throws Exception {
		System.out.println("se ha guardado la factura");
		
	}

}
