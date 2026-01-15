package es.deusto.sd.persistencia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.persistencia.entity.Archivo;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    
}
