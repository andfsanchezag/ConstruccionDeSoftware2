package app.adapter.in.validators;

import org.springframework.stereotype.Component;

@Component
public class InvoiceValidator extends SimpleValidator {
	
	public String productNameValidator(String value) throws Exception {
		return stringValidator("nombre del producto", value);
	}
	
	public long petIdValidator(String value) throws Exception {
		return longValidator("id de la mascota", value);
	}
	public long ownerDocumentValidator(String value) throws Exception {
		return longValidator("cedula del dueño", value);
	}
	
	public boolean isMedicineValidator(String value) throws Exception {
		stringValidator("venta de mediciona", value);
		return value.equals("si");
	}
	
	public long orderIdValidator(String value) throws Exception {
		return longValidator("id de la orden", value);
	}
	
	public double amountValidator(String value) throws Exception{
		return doubleValidator("precio de la factura", value);
	}

}
