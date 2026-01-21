 package es.deusto.sd.authenticus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchivoDTO {
    private Long idArchivo;
    private String nombre;
    private String ruta;

    public ArchivoDTO(){
    }

    public ArchivoDTO(Long idArchivo, String nombre, String ruta) {
        this.idArchivo = idArchivo;
        this.nombre = nombre;
        this.ruta = ruta;
    }
    public ArchivoDTO(String nombre, String ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
    }

    @JsonProperty("idArchivo")
    public Long getIdArchivo() {
        return idArchivo;
    }

    @JsonProperty("idArchivo")
    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
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

    
}
