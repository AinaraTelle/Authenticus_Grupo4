package es.deusto.sd.authenticus.dto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Data Transfer Object representing a Case")

public class CasoDTO {
    
    @Schema(description = "Unique identifier of the Case", example = "1")
    private Long IDCaso;
    @Schema(description = "name of the Case", example = "Caso 1")
    private String titulo;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private TipoAnalisisDTO tipoAnalisisDTO;
    @Schema(description = "creation date of case", example = "2019-04-23T15:00:00")//IMPORTANTE: RESPETAR EL FORMATO DE LA FECHA 
    private LocalDateTime fechaCreacion;
    @Schema(description = "List of attached files")
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
