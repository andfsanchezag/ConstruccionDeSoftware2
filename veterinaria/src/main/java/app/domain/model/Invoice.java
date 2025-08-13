package app.domain.model;


public class Invoice {
    private final String invoiceId;
    private final String petId;
    private final String ownerDocumentId;
    private final String orderId; // may be null if not medication
    private final String productName;
    private final double value;
    private final int quantity;
    private final String date;

    public Invoice(String invoiceId, String petId, String ownerDocumentId, String orderId,
                   String productName, double value, int quantity, String date) {
        this.invoiceId = invoiceId;
        this.petId = petId;
        this.ownerDocumentId = ownerDocumentId;
        this.orderId = orderId;
        this.productName = productName;
        this.value = value;
        this.quantity = quantity;
        this.date = date;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getProductName() {
        return productName;
    }

	public String getPetId() {
		return petId;
	}

	public String getOwnerDocumentId() {
		return ownerDocumentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public double getValue() {
		return value;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getDate() {
		return date;
	}
    
    
}
