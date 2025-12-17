package es.deusto.sd.authenticus.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long IDUsuario = null;
    private String nombre;
    private String email;
    private String password;
    private int tel;
    private Boolean login = false;
    private String token = null;

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

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
