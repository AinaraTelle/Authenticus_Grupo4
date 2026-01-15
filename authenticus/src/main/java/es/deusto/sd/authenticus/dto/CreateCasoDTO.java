package es.deusto.sd.authenticus.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateCasoDTO {
    @Schema(description = "name of the Case", example = "Caso 1")
    private String titulo;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private TipoAnalisisDTO tipoAnalisis;
    @Schema(description = "creation date of case", example = "2019-04-23T15:00:00")//IMPORTANTE: RESPETAR EL FORMATO DE LA FECHA 
    private LocalDateTime fechaCreacion;
    @Schema(description = "ArrayList of attached files")
    private ArrayList<CreateArchivoDTO> createArchivosDTO = new ArrayList<>();

    public CreateCasoDTO(){
    }
    public CreateCasoDTO( String titulo, TipoAnalisisDTO tipoAnalisis,
        LocalDateTime fechaCreacion,ArrayList<CreateArchivoDTO> createArchivosDTO){
        this.titulo= titulo;
        this.tipoAnalisis=tipoAnalisis;
        this.fechaCreacion=fechaCreacion;
        // this.createArchivosDTO = createArchivosDTO != null ? new ArrayArrayList<>(createArchivosDTO) : new ArrayArrayList<>();
        this.createArchivosDTO = createArchivosDTO;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public TipoAnalisisDTO getTipoAnalisis() {
        return tipoAnalisis;
    }
    public void setTipoAnalisis(TipoAnalisisDTO tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public ArrayList<CreateArchivoDTO> getCreateArchivosDTO() {
        return createArchivosDTO;
    }
    public void setCreateArchivosDTO(ArrayList<CreateArchivoDTO> createArchivosDTO) {
        this.createArchivosDTO = createArchivosDTO;
    }

    
}
