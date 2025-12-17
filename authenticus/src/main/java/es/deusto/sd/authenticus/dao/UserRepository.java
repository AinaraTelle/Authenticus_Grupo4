package es.deusto.sd.authenticus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.authenticus.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}