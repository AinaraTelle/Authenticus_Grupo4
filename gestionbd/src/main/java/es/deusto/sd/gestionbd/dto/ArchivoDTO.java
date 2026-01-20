package es.deusto.sd.gestionbd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchivoDTO {
    private Long IDArchivo;
    private String nombre;
    private String ruta;

    public ArchivoDTO(){
    }

    public ArchivoDTO(Long IDArchivo, String nombre, String ruta) {
        this.IDArchivo = IDArchivo;
        this.nombre = nombre;
        this.ruta = ruta;
    }
    public ArchivoDTO(String nombre, String ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
    }
    @JsonProperty("idArchivo")
    public Long getIDArchivo() {
        return IDArchivo;
    }
    public void setIDArchivo(Long IDArchivo) {
        this.IDArchivo = IDArchivo;
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
