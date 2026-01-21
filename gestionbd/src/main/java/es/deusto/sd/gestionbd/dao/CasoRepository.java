package es.deusto.sd.gestionbd.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.gestionbd.entity.Caso;
import es.deusto.sd.gestionbd.entity.User;
import java.util.List;

@Repository
public interface CasoRepository extends JpaRepository<Caso, Long> {
    List<Caso> findByUsuario(User usuario);
    void deleteById(Long idCaso);

}
