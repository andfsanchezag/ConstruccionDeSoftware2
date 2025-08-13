package app.domain.repository;



import java.util.List;
import java.util.Optional;

import app.domain.model.User;

public interface UserRepository {
    Optional<User> findByDocumentId(String documentId);
    Optional<User> findByUsername(String username);
    void save(User user);
    List<User> findAllByRole(String role);
}
