package app.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.infrastructure.persistence.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByDocument(long document);

	public UserEntity findByUserName(String userName);

}
