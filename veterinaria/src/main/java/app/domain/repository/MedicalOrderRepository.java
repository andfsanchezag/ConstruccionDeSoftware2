package app.domain.repository;



import java.util.List;
import java.util.Optional;

import app.domain.model.MedicalOrder;

public interface MedicalOrderRepository {
    Optional<MedicalOrder> findByOrderId(String orderId);
    List<MedicalOrder> findByVeterinarianId(String veterinarianDocumentId);
    List<MedicalOrder> findByPetId(String petId);
    void save(MedicalOrder medicalOrder);
}
