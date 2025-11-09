package es.deusto.sd.authenticus.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;


import es.deusto.sd.authenticus.entity.Archivo;
import es.deusto.sd.authenticus.entity.Caso;
import es.deusto.sd.authenticus.entity.Caso.TipoAnalisis;
import io.swagger.v3.oas.annotations.media.Schema;

public class ResultadosDTO {
    @Schema(description = "Unique identifier of the Case", example = "1")
    private int IDCaso;
    @Schema(description = "name of the Case", example = "Caso 1")
    private String titulo;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private Caso.TipoAnalisis tipoAnalisis;
    @Schema(description = "creation date of case", example = "2019-04-23T15:00:00")//IMPORTANTE: RESPETAR EL FORMATO DE LA FECHA 
    private LocalDateTime fechaCreacion;
    @Schema(description = "List of attached files")
    private HashMap<String, Integer> mapArchivos = new HashMap<>();


    
    public ResultadosDTO(int iDCaso, String titulo, TipoAnalisis tipoAnalisis, LocalDateTime fechaCreacion,
        List<Archivo> archivos) {
        IDCaso = iDCaso;
        this.titulo = titulo;
        this.tipoAnalisis = tipoAnalisis;
        this.fechaCreacion = fechaCreacion;
        for(Archivo ar1:archivos){
            if(tipoAnalisis.toString()==Caso.TipoAnalisis.ALTERAC_CONT.toString()){
                this.mapArchivos.put(ar1.getNombre(),0);
            }
            else if(tipoAnalisis.toString()==Caso.TipoAnalisis.VERACID_CONT.toString()){
                this.mapArchivos.put(ar1.getNombre(),1);
            }
        }
    }

    public int getIDCaso() {
        return IDCaso;
    }

    public void setIDCaso(int iDCaso) {
        IDCaso = iDCaso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Caso.TipoAnalisis getTipoAnalisis() {
        return tipoAnalisis;
    }

    public void setTipoAnalisis(Caso.TipoAnalisis tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public HashMap<String, Integer> getArchivos() {
        return mapArchivos;
    }

    public void setArchivos(HashMap<String, Integer> archivos) {
        this.mapArchivos = archivos;
    }


    
    
}
