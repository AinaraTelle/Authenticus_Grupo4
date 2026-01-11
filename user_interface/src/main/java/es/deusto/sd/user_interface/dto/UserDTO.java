package es.deusto.sd.user_interface.dto;

public class UserDTO{
    private Long IDUser;
    private String nombre;
    private String email;
    private String password;
    private int tel;

    public UserDTO(){
    }

    public UserDTO(String nombre, String email, String password, int tel) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public UserDTO(Long iDUser, String nombre, String email, String password, int tel) {
        this.IDUser = iDUser;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.tel = tel;
    }
    
    public Long getIDUser() {
        return IDUser;
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

    public void setIDUser(Long iDUser) {
        IDUser = iDUser;
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

    @Override
    public String toString() {
        return "UserDTO [IDUser=" + IDUser + ", nombre=" + nombre + ", email=" + email + ", password=" + password
                + ", tel=" + tel + "]";
    }

    
    
}
