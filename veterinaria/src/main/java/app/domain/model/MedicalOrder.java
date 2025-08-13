package veterinaria.domain.model;


public class MedicalOrder {
    private final String orderId;
    private final String petId;
    private final String ownerDocumentId;
    private final String veterinarianDocumentId;
    private final String medicationName;
    private final String dosage;
    private final String creationDate;
    private boolean isCancelled;

    public MedicalOrder(String orderId, String petId, String ownerDocumentId, String veterinarianDocumentId,
                        String medicationName, String dosage, String creationDate) {
        this.orderId = orderId;
        this.petId = petId;
        this.ownerDocumentId = ownerDocumentId;
        this.veterinarianDocumentId = veterinarianDocumentId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.creationDate = creationDate;
        this.isCancelled = false;
    }

    public void cancelOrder() {
        isCancelled = true;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getMedicationName() {
        return medicationName;
    }

	public String getPetId() {
		return petId;
	}

	public String getOwnerDocumentId() {
		return ownerDocumentId;
	}

	public String getVeterinarianDocumentId() {
		return veterinarianDocumentId;
	}

	public String getDosage() {
		return dosage;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
    
    
}
