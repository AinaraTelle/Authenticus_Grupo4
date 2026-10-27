package es.deusto.sd.user_interface.dto;


public class LoginRequestDTO {

    private String userEmail;
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