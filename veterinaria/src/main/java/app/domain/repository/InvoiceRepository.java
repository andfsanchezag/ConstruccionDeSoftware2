package app.domain.repository;



import java.util.List;

import app.domain.model.Invoice;

public interface InvoiceRepository {
    void save(Invoice invoice);
    List<Invoice> findByPetId(String petId);
    List<Invoice> findByOwnerDocumentId(String ownerDocumentId);
}
