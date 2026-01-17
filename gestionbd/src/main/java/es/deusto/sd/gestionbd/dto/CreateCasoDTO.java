package es.deusto.sd.gestionbd.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class CreateCasoDTO {
    private String titulo;
    private TipoAnalisisDTO tipoAnalisis;
    private LocalDateTime fechaCreacion;
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
