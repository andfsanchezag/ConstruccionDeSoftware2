package app.domain.repository;



import java.util.Optional;

import app.domain.model.MedicalRecord;

public interface MedicalRecordRepository {
    Optional<MedicalRecord> findByPetId(String petId);
    void save(MedicalRecord medicalRecord);
}
