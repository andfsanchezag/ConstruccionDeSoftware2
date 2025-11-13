package app.adapter.rest.response;

import java.sql.Date;

public class ClinicalOrderResponse {
    private long id;
    private long petId;
    private long ownerDocument;
    private long veterinarianDocument;
    private String medicine;
    private String doce;
    private Date date;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getPetId() { return petId; }
    public void setPetId(long petId) { this.petId = petId; }
    public long getOwnerDocument() { return ownerDocument; }
    public void setOwnerDocument(long ownerDocument) { this.ownerDocument = ownerDocument; }
    public long getVeterinarianDocument() { return veterinarianDocument; }
    public void setVeterinarianDocument(long veterinarianDocument) { this.veterinarianDocument = veterinarianDocument; }
    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }
    public String getDoce() { return doce; }
    public void setDoce(String doce) { this.doce = doce; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
