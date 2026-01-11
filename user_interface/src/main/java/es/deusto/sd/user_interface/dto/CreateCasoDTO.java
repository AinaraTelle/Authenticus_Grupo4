package es.deusto.sd.user_interface.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class CreateCasoDTO {
    private String titulo;
    private TipoAnalisisDTO tipoAnalisis;
    private LocalDateTime fechaCreacion;
    private ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();

    public CreateCasoDTO(){
    }
    public CreateCasoDTO( String titulo, TipoAnalisisDTO tipoAnalisis,
        LocalDateTime fechaCreacion,ArrayList<ArchivoDTO> archivosDTO){
        this.titulo= titulo;
        this.tipoAnalisis=tipoAnalisis;
        this.fechaCreacion=fechaCreacion;
        // this.archivosDTO = archivosDTO != null ? new ArrayArrayList<>(archivosDTO) : new ArrayArrayList<>();
        this.archivosDTO = archivosDTO;
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

    public ArrayList<ArchivoDTO> getArchivosDTO() {
        return archivosDTO;
    }

    public void setArchivosDTO(ArrayList<ArchivoDTO> archivosDTO) {
        this.archivosDTO = archivosDTO;
    }
}
