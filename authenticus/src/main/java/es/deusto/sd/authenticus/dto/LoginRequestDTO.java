package es.deusto.sd.authenticus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequestDTO {

    @Schema(description  = "El email es obligatorio")
    private String userEmail; // O solo 'username' o 'email'

    @Schema(description = "La contraseña es obligatoria")
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String usernameOrEmail, String password) {
        this.userEmail = usernameOrEmail;
        this.password = password;
    }

    public String getEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String usernameOrEmail) {
        this.userEmail = usernameOrEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}