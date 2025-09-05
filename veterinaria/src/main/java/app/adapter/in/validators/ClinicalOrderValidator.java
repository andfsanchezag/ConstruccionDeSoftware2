package app.adapter.in.validators;

import org.springframework.stereotype.Component;

@Component
public class ClinicalOrderValidator extends SimpleValidator {
	
	public String medicineValidator(String value) throws Exception{
		return stringValidator("medicina de la orden", value);
	}
	
	public String doceValidator(String value) throws Exception{
		return stringValidator("dosis de la medicina de la orden", value);
	}

}
