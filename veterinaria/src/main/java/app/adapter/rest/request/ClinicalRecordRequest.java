package app.adapter.rest.request;

public class ClinicalRecordRequest {
    private String veterinarianDocument;
    private String petId;
    private String orderId;
    private String motive;
    private String diagnosis;
    private String medicine;
    private String medicalProcedure;
    private String procedureDetail;
    private String vaccinationRecord;
    private String allergies;
    private String symptoms;
    private String doce;

    public String getVeterinarianDocument() { return veterinarianDocument; }
    public void setVeterinarianDocument(String veterinarianDocument) { this.veterinarianDocument = veterinarianDocument; }
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getMotive() { return motive; }
    public void setMotive(String motive) { this.motive = motive; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }
    public String getMedicalProcedure() { return medicalProcedure; }
    public void setMedicalProcedure(String medicalProcedure) { this.medicalProcedure = medicalProcedure; }
    public String getProcedureDetail() { return procedureDetail; }
    public void setProcedureDetail(String procedureDetail) { this.procedureDetail = procedureDetail; }
    public String getVaccinationRecord() { return vaccinationRecord; }
    public void setVaccinationRecord(String vaccinationRecord) { this.vaccinationRecord = vaccinationRecord; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public String getDoce() { return doce; }
    public void setDoce(String doce) { this.doce = doce; }
}
