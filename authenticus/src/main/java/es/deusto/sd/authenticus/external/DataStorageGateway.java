
package es.deusto.sd.authenticus.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;

@Component
public class DataStorageGateway implements IDataStorageGateway {

    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "http://localhost:8081/usuarios";

    @Override
    public UserDTO registerUser(RegisterRequestDTO userRegisterDTO) {
        return restTemplate.postForObject(URL+"/register", userRegisterDTO, UserDTO.class);
    };
    

    

}



