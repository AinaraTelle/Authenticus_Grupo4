package es.deusto.sd.authenticus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ResultadosDTO {
    @Schema(description = "Unique identifier of the Archivo", example = "1")
    private Long idArchivo;
    @Schema(description = "value of the results", example = "Caso 1")
    private double valor;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private TipoAnalisisDTO tipoAnalisis;

    public ResultadosDTO() {
    }

    public ResultadosDTO(Long idArchivo, double valor,
    TipoAnalisisDTO tipoAnalisis) {
        this.idArchivo = idArchivo;
        this.valor = valor;
        this.tipoAnalisis = tipoAnalisis;
    }

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoAnalisisDTO getTipoAnalisis() {
        return tipoAnalisis;
    }

    public void setTipoAnalisis(TipoAnalisisDTO tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }

    
}

