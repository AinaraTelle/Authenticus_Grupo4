package es.deusto.sd.gestionbd.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.gestionbd.entity.Archivo;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    
}
