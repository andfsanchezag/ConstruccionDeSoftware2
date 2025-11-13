package app.adapter.rest.request;

public class InvoiceRequest {
    private String petId;
    private String ownerDocument;
    private String productAmount;
    private String productName;
    private String isMedicine; // "si" or "no"
    private String orderId; // optional when isMedicine is "si"

    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    public String getOwnerDocument() { return ownerDocument; }
    public void setOwnerDocument(String ownerDocument) { this.ownerDocument = ownerDocument; }
    public String getProductAmount() { return productAmount; }
    public void setProductAmount(String productAmount) { this.productAmount = productAmount; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getIsMedicine() { return isMedicine; }
    public void setIsMedicine(String isMedicine) { this.isMedicine = isMedicine; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
}
