package es.deusto.sd.authenticus.entity;

public class User {
    private int IDUsuario;
    private String nombre;
    private String email;
    private String password;
    private int tel;

    public User(int iDUsuario, String nombre, String email, String password, int tel) {
        IDUsuario = iDUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tel = tel;

        
    }

    public int getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(int iDUsuario) {
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

    
}
