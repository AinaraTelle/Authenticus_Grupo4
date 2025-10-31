package es.deusto.sd.authenticus.entity;

public class User {
    private int IDUsuario;
    private String email;
    private String password;
    private String nombre;
    private int tel;

    public User(int iDUsuario, String email, String password, String nombre, int tel) {
        IDUsuario = iDUsuario;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.tel = tel;
    }

    public void setIDUsuario(int iDUsuario) {
        IDUsuario = iDUsuario;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTel(int tel) {
        this.tel = tel;
    }
    public int getIDUsuario() {
        return IDUsuario;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getNombre() {
        return nombre;
    }
    public int getTel() {
        return tel;
    }    
}
