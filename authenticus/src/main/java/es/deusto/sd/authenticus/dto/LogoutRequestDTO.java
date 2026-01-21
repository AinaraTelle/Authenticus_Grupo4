package es.deusto.sd.authenticus.dto;
import io.swagger.v3.oas.annotations.media.Schema;

public class LogoutRequestDTO {
    @Schema(description  = "El token es necesario para el logout")
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
