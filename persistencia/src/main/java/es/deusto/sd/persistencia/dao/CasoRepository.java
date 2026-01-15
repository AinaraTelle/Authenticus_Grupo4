package es.deusto.sd.persistencia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.persistencia.entity.Caso;
import es.deusto.sd.persistencia.entity.User;
import java.util.List;

@Repository
public interface CasoRepository extends JpaRepository<Caso, Long> {
    List<Caso> findByUsuario(User usuario);

}
