package es.deusto.sd.authenticus.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Caso {
    public enum TipoAnalisis{
        ALTERAC_CONT, VERACID_CONT
    }


    private int IDCaso;
    private String titulo;
    private Caso.TipoAnalisis tipoAnalisis;
    private LocalDateTime fechaCreacion;
    private List<Archivo> archivos = new ArrayList<>();

    public Caso(int IdCaso, String titulo, Caso.TipoAnalisis tipoAnalisis,LocalDateTime fechaCreacion){
        IDCaso=IdCaso;
        this.titulo= titulo;
        this.tipoAnalisis=tipoAnalisis;
        this.fechaCreacion=fechaCreacion;
    }

    
    public int getIDCaso() {
        return IDCaso;
    }
    public String getTitulo() {
        return titulo;
    }
    public Caso.TipoAnalisis getTipoAnalisis() {
        return tipoAnalisis;
    }
    public void setIDCaso(int iDCaso) {
        IDCaso = iDCaso;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setTipoAnalisis(Caso.TipoAnalisis tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion= fechaCreacion;
    }

    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }

    public List<Archivo> getArchivos() { 
        return archivos; 
    }
    public void setArchivos(List<Archivo> archivos) { 
        this.archivos = archivos; 
    }

    public void addArchivo(Archivo archivo) {
        this.archivos.add(archivo);
    }

    
}

