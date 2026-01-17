package es.deusto.sd.gestionbd.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_tokens")

public class UserToken {
    @Id
    private Long id; // Esta será la PK, pero NO es autoincremental

    @OneToOne
    @MapsId // <--- El ID de arriba es el mismo que el del 'user' 
    @JoinColumn(name = "IDUsuario") // Nombre de la columna en la tabla física
    private User user;

    @Column(name = "token")
    private String token;

    
    public UserToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
