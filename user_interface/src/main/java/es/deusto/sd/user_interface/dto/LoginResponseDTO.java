package es.deusto.sd.authenticus.dto;
import io.swagger.v3.oas.annotations.media.Schema;

public class LoginResponseDTO {
    @Schema(description  = "Devuelve el email del usuario logeado")
    private String email;
    @Schema(description  = "devuelve el token correspondiente, lo importante de esta clase")
    private String token;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
