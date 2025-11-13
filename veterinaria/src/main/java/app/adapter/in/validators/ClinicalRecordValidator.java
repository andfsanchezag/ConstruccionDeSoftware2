package app.adapter.in.validators;

import org.springframework.stereotype.Component;

@Component
public class ClinicalRecordValidator extends SimpleValidator {
	
	public String motiveValidator(String value) throws Exception{
		return stringValidator("motivo de la consulta", value);
	}
	public String diagnosisValidator(String value) throws Exception{
		return stringValidator("diagnostico de la consulta", value);
	}
	public String medicineValidator(String value) throws Exception{
		return stringValidator("medicina", value);
	}
	public String doceValidator(String value) throws Exception{
		return stringValidator("dosis de la medicina", value);
	}
	public String procedureValidator(String value) throws Exception{
		return stringValidator("nombre del procedimiento", value);
	}
	public String procedureDetailsValidator(String value) throws Exception{
		return stringValidator("detalles del procedimiento", value);
	}
	public String allergiesValidator(String value) throws Exception{
		return stringValidator("alergias ", value);
	}
	public String vaccinationValidator(String value) throws Exception{
		return stringValidator("vacunacion ", value);
	}
	public String symptomsValidator(String value) throws Exception{
		return stringValidator("sintomas ", value);
	}
	


}
