package veterinaria.domain.repository;



import java.util.Optional;

import veterinaria.domain.model.MedicalRecord;

public interface MedicalRecordRepository {
    Optional<MedicalRecord> findByPetId(String petId);
    void save(MedicalRecord medicalRecord);
}
