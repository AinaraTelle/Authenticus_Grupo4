package es.deusto.sd.gestionbd.dto;

public class UserTokenDTO {
    private String email;
    private String token;

    public UserTokenDTO() {
    }

    public UserTokenDTO(String email, String token) {
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
