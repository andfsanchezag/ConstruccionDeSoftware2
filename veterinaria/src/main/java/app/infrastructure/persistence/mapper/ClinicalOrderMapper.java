package app.infrastructure.persistence.mapper;

import app.domain.model.ClinicalOrder;
import app.infrastructure.persistence.entities.ClinicalOrderEntity;

public class ClinicalOrderMapper {

    // Dominio → Entidad
    public static ClinicalOrderEntity toEntity(ClinicalOrder domain) {
        if (domain == null) return null;

        ClinicalOrderEntity entity = new ClinicalOrderEntity();
        entity.setId(domain.getId());

        // Mapeo de relaciones (se delega a los mappers de User y Pet)
        entity.setPet(PetMapper.toEntity(domain.getPet()));
        entity.setOwner(UserMapper.toEntity(domain.getOwner()));
        entity.setVeterinarian(UserMapper.toEntity(domain.getVeterinarian()));

        entity.setMedicine(domain.getMedicine());
        entity.setDose(domain.getDoce()); // corregido "doce" → "dose"
        entity.setDate(domain.getDate());

        return entity;
    }

    // Entidad → Dominio
    public static ClinicalOrder toDomain(ClinicalOrderEntity entity) {
        if (entity == null) return null;

        ClinicalOrder domain = new ClinicalOrder();
        domain.setId(entity.getId());

        domain.setOwner(UserMapper.toDomain(entity.getOwner()));
        domain.setPet(PetMapper.toDomain(entity.getPet()));
        domain.setVeterinarian(UserMapper.toDomain(entity.getVeterinarian()));

        domain.setMedicine(entity.getMedicine());
        domain.setDoce(entity.getDose()); // mantiene "doce" en el dominio
        domain.setDate(entity.getDate());

        return domain;
    }
}