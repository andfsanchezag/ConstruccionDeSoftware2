package app.adapter.rest.request;

public class ClinicalOrderRequest {
    private String veterinarianDocument;
    private String petId;
    private String medicine;
    private String doce;

    public String getVeterinarianDocument() { return veterinarianDocument; }
    public void setVeterinarianDocument(String veterinarianDocument) { this.veterinarianDocument = veterinarianDocument; }
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }
    public String getDoce() { return doce; }
    public void setDoce(String doce) { this.doce = doce; }
}
