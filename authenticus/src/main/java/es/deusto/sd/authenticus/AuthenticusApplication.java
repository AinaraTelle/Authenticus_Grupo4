package es.deusto.sd.authenticus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class AuthenticusApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticusApplication.class, args);
	}
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
