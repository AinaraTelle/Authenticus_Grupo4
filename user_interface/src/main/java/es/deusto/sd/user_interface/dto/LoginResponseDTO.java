package es.deusto.sd.user_interface.dto;

public class LoginResponseDTO {
    private String email;
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
