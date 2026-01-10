package es.deusto.sd.authenticus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Data Transfer Object representing a User")
public class UserDTO{
    @Schema(description = "Unique identifier of the User", example = "1")
    private Long IDUser;
    @Schema(description = "name of the User", example = "Jose")
    private String nombre;
    @Schema(description = "email of the User", example = "jose02@gmail.com")
    private String email;
    @Schema(description = "passowrd of the User", example = "contraseña")
    private String password;
    @Schema(description = "phone number of the User", example = "613458245")
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
