package es.deusto.sd.authenticus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Data Transfer Object representing a Usuario")
public class UserDTO{
    @Schema(description = "Unique identifier of the Usuario", example = "1")
    private int IDUsuario;
    @Schema(description = "name of the Usuario", example = "Jose")
    private String nombre;
    @Schema(description = "email of the Usuario", example = "jose02@gmail.com")
    private String email;
    @Schema(description = "passowrd of the Usuario", example = "contraseña")
    private String password;
    @Schema(description = "phone number of the Usuario", example = "613458245")
    private int tel;

    public UserDTO(){
    }

    public UserDTO(String nombre, String email, String password, int tel) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.tel = tel;
    }

    public UserDTO(int iDUsuario, String nombre, String email, String password, int tel) {
        IDUsuario = iDUsuario;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
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

    
    
}
