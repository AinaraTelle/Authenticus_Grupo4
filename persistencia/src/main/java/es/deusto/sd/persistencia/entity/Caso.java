package es.deusto.sd.persistencia.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "casos")

public class Caso {
    
    //Autogeneramos el ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDCaso;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private TipoAnalisis tipoAnalisis;

    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archivo> archivos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "IDUsuario")
    private User usuario;

    public Caso(){
    }

    public Caso(String titulo,
        LocalDateTime fechaCreacion, User usuario){
        this.titulo= titulo;
        this.fechaCreacion=fechaCreacion;
        this.usuario = usuario;
    }
    

    public Long getIDCaso() {
        return IDCaso;
    }

    public void setIDCaso(Long iDCaso) {
        IDCaso = iDCaso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoAnalisis getTipoAnalisis() {
        return tipoAnalisis;
    }

    public void setTipoAnalisis(TipoAnalisis tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivo> archivos) {
        this.archivos = archivos;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

}

