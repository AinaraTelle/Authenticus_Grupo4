package es.deusto.sd.authenticus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class AuthenticusApplication {

	public static void main(String[] args) {
		// new Thread(() -> {
        //     try {
        //         new ServidorSocket(); // ejecuta el servidor socket
        //     } catch (Exception e) {
        //         System.err.println("Error iniciando servidor socket: " + e.getMessage());
        //     }
        // }).start();
		SpringApplication.run(AuthenticusApplication.class, args);
	}
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
