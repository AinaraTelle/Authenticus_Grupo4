package es.deusto.sd.authenticus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ResultadosDTO {
    @Schema(description = "Unique identifier of the Archivo", example = "1")
    private int IDArchivo;
    @Schema(description = "value of the results", example = "Caso 1")
    private double valor;
    @Schema(description = "type of case", example = "ALTERAC_CONT")
    private TipoAnalisisDTO tipoAnalisis;


    public ResultadosDTO() {
    }

    public ResultadosDTO(int iDArchivo, double valor,
    TipoAnalisisDTO tipoAnalisis) {
        IDArchivo = iDArchivo;
        this.valor = valor;
        this.tipoAnalisis = tipoAnalisis;
    }

    public int getIDArchivo() {
        return IDArchivo;
    }

    public void setIDArchivo(int iDArchivo) {
        IDArchivo = iDArchivo;
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

