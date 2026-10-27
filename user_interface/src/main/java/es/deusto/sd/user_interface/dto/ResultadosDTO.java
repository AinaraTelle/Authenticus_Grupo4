package es.deusto.sd.user_interface.dto;


public class ResultadosDTO {
    private Long idArchivo;
    private double valor;
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

