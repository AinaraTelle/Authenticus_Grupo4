package es.deusto.sd.user_interface.dto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CasoDTO {
    
    private Long IDCaso;
    private String titulo;
    private TipoAnalisisDTO tipoAnalisisDTO;
    private LocalDateTime fechaCreacion;
    private ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();

    public CasoDTO(){
    }
    public CasoDTO(Long IdCaso, String titulo, 
        LocalDateTime fechaCreacion,
        List<ArchivoDTO> archivosDTO){
        IDCaso=IdCaso;
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
    public TipoAnalisisDTO getTipoAnalisis() {
        return tipoAnalisisDTO;
    }
    public void setIDCaso(Long iDCaso) {
        IDCaso = iDCaso;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setTipoAnalisisDTO(TipoAnalisisDTO tipoAnalisisDTO) {
        this.tipoAnalisisDTO = tipoAnalisisDTO;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion= fechaCreacion;
    }

    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }

    public ArrayList<ArchivoDTO> getarchivosDTO() {
        return archivosDTO;
    }

    public void setarchivosDTO(ArrayList<ArchivoDTO> archivosDTO) {
        this.archivosDTO = archivosDTO;
    }
}
