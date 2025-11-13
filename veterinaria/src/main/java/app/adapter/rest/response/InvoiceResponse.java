package app.adapter.rest.response;

import java.sql.Date;

public class InvoiceResponse {
    private long id;
    private long petId;
    private long ownerDocument;
    private String productName;
    private double productAmount;
    private boolean medicine;
    private Long orderId; // nullable when no medicine
    private Date date;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getPetId() { return petId; }
    public void setPetId(long petId) { this.petId = petId; }
    public long getOwnerDocument() { return ownerDocument; }
    public void setOwnerDocument(long ownerDocument) { this.ownerDocument = ownerDocument; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getProductAmount() { return productAmount; }
    public void setProductAmount(double productAmount) { this.productAmount = productAmount; }
    public boolean isMedicine() { return medicine; }
    public void setMedicine(boolean medicine) { this.medicine = medicine; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
