package es.deusto.sd.authenticus.dto;
import java.time.LocalDateTime;
import java.util.ArrayList;

import es.deusto.sd.authenticus.entity.Caso;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Data Transfer Object representing a Case")

public class CasoDTO {
    @Schema(description = "Unique identifier of the Case", example = "1")
    private int IDCaso;
    @Schema(description = "name of the Case", example = "Caso 1")
    private String titulo;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private Caso.TipoAnalisis tipoAnalisis;
    @Schema(description = "creation date of case", example = "2019-04-23T15:00:00")//IMPORTANTE: RESPETAR EL FORMATO DE LA FECHA 
    private LocalDateTime fechaCreacion;

    //para lo de los archivos
    @Schema(description = "List of attached files")
    private ArrayList<String> archivos = new ArrayList<>();

    public CasoDTO(){
    }
    public CasoDTO(int IdCaso, String titulo, Caso.TipoAnalisis tipoAnalisis,LocalDateTime fechaCreacion){
        IDCaso=IdCaso;
        this.titulo= titulo;
        this.tipoAnalisis=tipoAnalisis;
        this.fechaCreacion=fechaCreacion;

        //para lo de los archivos
        this.archivos = archivos;
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
