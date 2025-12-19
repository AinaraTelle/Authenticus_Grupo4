package es.deusto.sd.authenticus.dto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.deusto.sd.authenticus.entity.Archivo;
import es.deusto.sd.authenticus.entity.Caso;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Data Transfer Object representing a Case")

public class CasoDTO {
    @Schema(description = "Unique identifier of the Case", example = "1")
    private Long IDCaso;
    @Schema(description = "name of the Case", example = "Caso 1")
    private String titulo;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private Caso.TipoAnalisis tipoAnalisis;
    @Schema(description = "creation date of case", example = "2019-04-23T15:00:00")//IMPORTANTE: RESPETAR EL FORMATO DE LA FECHA 
    private LocalDateTime fechaCreacion;
    @Schema(description = "List of attached files")
    private ArrayList<Archivo> archivos = new ArrayList<>();

    public CasoDTO(){
    }
    public CasoDTO(Long IdCaso, String titulo, Caso.TipoAnalisis tipoAnalisis,LocalDateTime fechaCreacion,List<Archivo> archivos){
        IDCaso=IdCaso;
        this.titulo= titulo;
        this.tipoAnalisis=tipoAnalisis;
        this.fechaCreacion=fechaCreacion;
        this.archivos = archivos != null ? new ArrayList<>(archivos) : new ArrayList<>();
    }

    
    public Long getIDCaso() {
        return IDCaso;
    }
    public String getTitulo() {
        return titulo;
    }
    public Caso.TipoAnalisis getTipoAnalisis() {
        return tipoAnalisis;
    }
    public void setIDCaso(Long iDCaso) {
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

    public ArrayList<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(ArrayList<Archivo> archivos) {
        this.archivos = archivos;
    }
}
