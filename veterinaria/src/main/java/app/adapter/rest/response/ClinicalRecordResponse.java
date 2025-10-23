package app.adapter.rest.response;

import java.sql.Date;

public class ClinicalRecordResponse {
    private long id;
    private long petId;
    private long veterinarianDocument;
    private long orderId;
    private Date date;
    private String motive;
    private String diagnosis;
    private String medicine;
    private String medicalProcedure;
    private String procedureDetail;
    private String vaccinationRecord;
    private String allergies;
    private String symptoms;
    private boolean status;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getPetId() { return petId; }
    public void setPetId(long petId) { this.petId = petId; }
    public long getVeterinarianDocument() { return veterinarianDocument; }
    public void setVeterinarianDocument(long veterinarianDocument) { this.veterinarianDocument = veterinarianDocument; }
    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
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
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
