package es.deusto.sd.authenticus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequestDTO {

    @Schema(description  = "El nombre de usuario o email es obligatorio")
    private String userEmail; // O solo 'username' o 'email'

    @Schema(description = "La contraseña es obligatoria")
    private String password;

    // --- Constructor sin argumentos (necesario para Spring/Jackson) ---
    public LoginRequestDTO() {
    }

    // --- Constructor con todos los argumentos (opcional, pero útil) ---
    public LoginRequestDTO(String usernameOrEmail, String password) {
        this.userEmail = usernameOrEmail;
        this.password = password;
    }

    // --- Getters (Necesarios para que Spring lea los datos) ---
    
    public String getUsernameOrEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    // --- Setters (Opcionales, pero Spring los puede necesitar si no usas records o Lombok) ---
    
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.userEmail = usernameOrEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}