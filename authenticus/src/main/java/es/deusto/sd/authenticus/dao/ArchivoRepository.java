package es.deusto.sd.authenticus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.authenticus.entity.Archivo;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    
}
