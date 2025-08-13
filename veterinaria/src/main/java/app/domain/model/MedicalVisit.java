package app.domain.model;


import java.util.List;

public class MedicalVisit {
    private final String date;
    private final String veterinarianDocumentId;
    private final String reason;
    private final String symptoms;
    private final String diagnosis;
    private final ProcedureType procedureType;
    private final String procedureDetail;
    private final String medicationName;
    private final String dosage;
    private final String orderId;
    private final List<Vaccine> vaccinationHistory;
    private final List<Allergy> allergies;
    private boolean isOrderCancelled;

    public MedicalVisit(String date,
                        String veterinarianDocumentId,
                        String reason,
                        String symptoms,
                        String diagnosis,
                        ProcedureType procedureType,
                        String procedureDetail,
                        String medicationName,
                        String dosage,
                        String orderId,
                        List<Vaccine> vaccinationHistory,
                        List<Allergy> allergies) {
        this.date = date;
        this.veterinarianDocumentId = veterinarianDocumentId;
        this.reason = reason;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.procedureType = procedureType;
        this.procedureDetail = procedureDetail;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.orderId = orderId;
        this.vaccinationHistory = vaccinationHistory;
        this.allergies = allergies;
        this.isOrderCancelled = false;
    }

    public void cancelOrder() {
        isOrderCancelled = true;
    }

    public boolean isOrderCancelled() {
        return isOrderCancelled;
    }

    public String getDate() {
        return date;
    }

    public String getVeterinarianDocumentId() {
        return veterinarianDocumentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getOrderId() {
        return orderId;
    }

	public String getReason() {
		return reason;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public ProcedureType getProcedureType() {
		return procedureType;
	}

	public String getProcedureDetail() {
		return procedureDetail;
	}

	public String getMedicationName() {
		return medicationName;
	}

	public String getDosage() {
		return dosage;
	}

	public List<Vaccine> getVaccinationHistory() {
		return vaccinationHistory;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setOrderCancelled(boolean isOrderCancelled) {
		this.isOrderCancelled = isOrderCancelled;
	}
    
    
}
