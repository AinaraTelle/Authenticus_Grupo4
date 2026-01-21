package es.deusto.sd.gestionbd.dao;

import java.util.Optional;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.gestionbd.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByToken(String token);
    void deleteByToken(String token);
    Optional<UserToken> findById(Long id);
}