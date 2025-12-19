package es.deusto.sd.authenticus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.authenticus.entity.Caso;
import es.deusto.sd.authenticus.entity.User;

@Repository
public interface CasoRepository extends JpaRepository<Caso, Long> {
    List<Caso> findByUsuario(User usuario);

}
