package es.deusto.sd.authenticus.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long IDUsuario;
    private String nombre;
    private String email;
    private String password;
    private int tel;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Caso> casos = new ArrayList<>();

    
    
    public User(){
    }
    public User(Long iDUsuario, String nombre, String email, String password, int tel) {
        IDUsuario = iDUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public Long getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(Long iDUsuario) {
        IDUsuario = iDUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }
    public List<Caso> getCasos() {
        return casos;
    }
    public void setCasos(List<Caso> casos) {
        this.casos = casos;
    }

}
