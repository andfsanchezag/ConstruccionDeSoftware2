package app.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.infrastructure.persistence.entities.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
	public PetEntity findById(long Id);
	

}
