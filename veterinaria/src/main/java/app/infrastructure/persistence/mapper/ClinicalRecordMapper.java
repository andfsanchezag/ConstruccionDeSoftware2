package app.infrastructure.persistence.mapper;

import app.domain.model.ClinicalRecord;
import app.infrastructure.persistence.entities.ClinicalRecordEntity;

public class ClinicalRecordMapper {


    public static ClinicalRecord toDomain(ClinicalRecordEntity entity) {
        if (entity == null) return null;

        ClinicalRecord record = new ClinicalRecord();
        record.setId(entity.getId());
        record.setPet(PetMapper.toDomain(entity.getPet()));
        record.setVeterinarian(UserMapper.toDomain(entity.getVeterinarian()));
        record.setDate(entity.getDate());
        record.setMotive(entity.getMotive());
        record.setDiagnosis(entity.getDiagnosis());
        record.setMedicine(entity.getMedicine());
        record.setMedicalProcedure(entity.getMedicalProcedure());
        record.setDoce(entity.getDose()); 
        record.setClinicalOrder(ClinicalOrderMapper.toDomain(entity.getClinicalOrder()));
        record.setVaccinationRecord(entity.getVaccinationRecord());
        record.setAllergies(entity.getAllergies());
        record.setProceddureDetail(entity.getProcedureDetail());
        record.setSymptoms(entity.getSymptoms());
        record.setStatus(entity.isStatus());
        return record;
    }

    public static ClinicalRecordEntity toEntity(ClinicalRecord domain) {
        if (domain == null) return null;

        ClinicalRecordEntity entity = new ClinicalRecordEntity();
        entity.setId(domain.getId());
        entity.setPet(PetMapper.toEntity(domain.getPet()));
        entity.setVeterinarian(UserMapper.toEntity(domain.getVeterinarian()));
        entity.setDate(domain.getDate());
        entity.setMotive(domain.getMotive());
        entity.setDiagnosis(domain.getDiagnosis());
        entity.setMedicine(domain.getMedicine());
        entity.setMedicalProcedure(domain.getMedicalProcedure());
        entity.setDose(domain.getDoce()); // campo corregido
        entity.setClinicalOrder(ClinicalOrderMapper.toEntity(domain.getClinicalOrder()));
        entity.setVaccinationRecord(domain.getVaccinationRecord());
        entity.setAllergies(domain.getAllergies());
        entity.setProcedureDetail(domain.getProceddureDetail());
        entity.setSymptoms(domain.getSymptoms());
        entity.setStatus(domain.isStatus());
        return entity;
    }
}
