package es.deusto.sd.authenticus.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterRequestDTO {

    @Schema(description = "name of the User", example = "Jose")
    private String nombre;
    @Schema(description = "email of the User", example = "jose02@gmail.com")
    private String email;
    @Schema(description = "passowrd of the User", example = "contraseña")
    private String password;
    @Schema(description = "phone number of the User", example = "613458245")
    private int tel;

    public RegisterRequestDTO(){
    }

    public RegisterRequestDTO( String nombre, String email, String password, int tel) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tel = tel;
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
        return "RegisterRequestDTO [nombre=" + nombre + ", email=" + email + ", password=" + password
                + ", tel=" + tel + "]";
    }
}

