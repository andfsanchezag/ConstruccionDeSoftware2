package veterinaria.domain.repository;



import java.util.List;

import veterinaria.domain.model.Invoice;

public interface InvoiceRepository {
    void save(Invoice invoice);
    List<Invoice> findByPetId(String petId);
    List<Invoice> findByOwnerDocumentId(String ownerDocumentId);
}
