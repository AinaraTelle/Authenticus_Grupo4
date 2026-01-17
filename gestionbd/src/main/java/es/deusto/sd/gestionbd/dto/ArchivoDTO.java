package es.deusto.sd.gestionbd.dto;

public class ArchivoDTO {
    private Long IDArchivo;
    private String nombre;
    private String ruta;

    public ArchivoDTO(){
    }

    public ArchivoDTO(Long iDArchivo, String nombre, String ruta) {
        IDArchivo = iDArchivo;
        this.nombre = nombre;
        this.ruta = ruta;
    }
    public ArchivoDTO(String nombre, String ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
    }
    public Long getIDArchivo() {
        return IDArchivo;
    }
    public void setIDArchivo(Long iDArchivo) {
        IDArchivo = iDArchivo;
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
