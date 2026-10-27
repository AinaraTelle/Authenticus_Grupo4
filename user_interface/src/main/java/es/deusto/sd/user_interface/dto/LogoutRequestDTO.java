package es.deusto.sd.user_interface.dto;

public class LogoutRequestDTO {
    private String token;
    
    public LogoutRequestDTO() {
    }
    public LogoutRequestDTO(String token) {
        this.token = token;
    }
    
    public String getToken(){
        return token;
    }
}
