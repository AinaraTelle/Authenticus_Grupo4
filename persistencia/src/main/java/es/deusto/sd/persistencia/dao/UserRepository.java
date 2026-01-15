package es.deusto.sd.persistencia.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.persistencia.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByEmailAndPassword(String userEmail, String password);
    Optional<User> findByEmail(String userEmail);
    void deleteById(Long id);
}