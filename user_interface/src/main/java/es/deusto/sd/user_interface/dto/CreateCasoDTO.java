package es.deusto.sd.authenticus.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateCasoDTO {
    @Schema(description = "name of the Case", example = "Caso 1")
    private String titulo;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private TipoAnalisisDTO tipoAnalisis;
    @Schema(description = "creation date of case", example = "2019-04-23T15:00:00")//IMPORTANTE: RESPETAR EL FORMATO DE LA FECHA 
    private LocalDateTime fechaCreacion;
    @Schema(description = "List of attached files")
    private ArrayList<ArchivoDTO> ArchivoDTOs = new ArrayList<>();

    public CreateCasoDTO(){
    }
    public CreateCasoDTO( String titulo, TipoAnalisisDTO tipoAnalisis,LocalDateTime fechaCreacion,List<ArchivoDTO> ArchivoDTOs){
        this.titulo= titulo;
        this.tipoAnalisis=tipoAnalisis;
        this.fechaCreacion=fechaCreacion;
        this.ArchivoDTOs = ArchivoDTOs != null ? new ArrayList<>(ArchivoDTOs) : new ArrayList<>();
    }

    
    public String getTitulo() {
        return titulo;
    }
    public TipoAnalisisDTO getTipoAnalisis() {
        return tipoAnalisis;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setTipoAnalisis(TipoAnalisisDTO tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion= fechaCreacion;
    }

    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }

    public ArrayList<ArchivoDTO> getArchivoDTOs() {
        return ArchivoDTOs;
    }

    public void setArchivoDTOs(ArrayList<ArchivoDTO> ArchivoDTOs) {
        this.ArchivoDTOs = ArchivoDTOs;
    }
}
