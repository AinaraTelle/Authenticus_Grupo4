package es.deusto.sd.authenticus.dto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CasoDTO {
    
    private Long IDCaso;
    private String titulo;
    private TipoAnalisisDTO tipoAnalisisDTO;
    private LocalDateTime fechaCreacion;
    private ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();

    public CasoDTO(){
    }
    public CasoDTO(Long IDCaso, String titulo, 
        LocalDateTime fechaCreacion, 
        List<ArchivoDTO> archivosDTO){
        this.IDCaso = IDCaso;
        this.titulo= titulo;
        this.fechaCreacion=fechaCreacion;
        this.archivosDTO = archivosDTO != null ? new ArrayList<>(archivosDTO) : new ArrayList<>();
    }

    public CasoDTO(Long IDCaso, String titulo, 
        LocalDateTime fechaCreacion){
        this.IDCaso = IDCaso;
        this.titulo= titulo;
        this.fechaCreacion=fechaCreacion;
    }

    public CasoDTO(String titulo, 
        LocalDateTime fechaCreacion,
        List<ArchivoDTO> archivosDTO){
        this.titulo= titulo;
        this.fechaCreacion=fechaCreacion;
        this.archivosDTO = archivosDTO != null ? new ArrayList<>(archivosDTO) : new ArrayList<>();
    }
    
    public Long getIDCaso() {
        return IDCaso;
    }
    public String getTitulo() {
        return titulo;
    }
    public TipoAnalisisDTO getTipoAnalisisDTO() {
        return tipoAnalisisDTO;
    }
    public void setIDCaso(Long IDCaso) {
        this.IDCaso = IDCaso;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // @JsonProperty("tipoAnalisisDTO")
    public void setTipoAnalisisDTO(TipoAnalisisDTO tipoAnalisisDTO) {
        this.tipoAnalisisDTO = tipoAnalisisDTO;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion= fechaCreacion;
    }

    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }

    public ArrayList<ArchivoDTO> getArchivosDTO() {
        return archivosDTO;
    }

    public void setArchivosDTO(ArrayList<ArchivoDTO> archivosDTO) {
        this.archivosDTO = archivosDTO;
    }
}
