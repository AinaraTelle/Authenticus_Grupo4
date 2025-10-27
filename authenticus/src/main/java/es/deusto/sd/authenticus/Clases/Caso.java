package es.deusto.sd.authenticus.Clases;

public class Caso {
    public enum TipoAnalisis{
        ALTERAC_CONT, VERACID_CONT, ALTERAC_VERACID_CONT
    }


    private int IDCaso;
    private String titulo;
    private Caso.TipoAnalisis tipoAnalisis;

    
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

    

    
}

