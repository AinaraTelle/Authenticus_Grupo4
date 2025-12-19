package es.deusto.sd.authenticus.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "archivos")

public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDArchivo;

    private String nombre;
    private String ruta;

    @ManyToOne
    @JoinColumn(name = "caso_id")
    private Caso caso;

    //OBLIGATORIO
    public Archivo() {
    }

    public Archivo(String nombre, String ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
    }

    public Long getId() {
        return IDArchivo;
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getRuta() { 
        return ruta; 
    }
    public void setRuta(String ruta) { 
        this.ruta = ruta; 
    }

    public void setCaso(Caso caso){
        this.caso = caso;
    }
}
