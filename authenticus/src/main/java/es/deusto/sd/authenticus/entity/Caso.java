package es.deusto.sd.authenticus.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Caso {
    public enum TipoAnalisis{
        ALTERAC_CONT, VERACID_CONT, ALTERAC_VERACID_CONT
    }


    private int IDCaso;
    private String titulo;
    private Caso.TipoAnalisis tipoAnalisis;
    private LocalDateTime fechaCreacion;

    //para lo de anadir archivos
    private ArrayList<String> archivos = new ArrayList<>();

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

    //para lo de los archivos
    public ArrayList<String> getArchivos() {
        return archivos;
    }

    public void setArchivos(ArrayList<String> archivos) {
        this.archivos = archivos;
    }

    
}

