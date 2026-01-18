
package es.deusto.sd.authenticus.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.dto.UserTokenDTO;

@Component
public class DataStorageGateway implements IDataStorageGateway {

    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "http://localhost:8081/usuarios";

    @Override
    public UserDTO registerUser(RegisterRequestDTO userRegisterDTO) {
        return restTemplate.postForObject(URL+"/register", userRegisterDTO, UserDTO.class);
    };

    @Override
    public UserTokenDTO loginUser(LoginRequestDTO userLogin){
        UserTokenDTO userTokenDTO= restTemplate.postForObject(URL+"/login", userLogin, UserTokenDTO.class);
        if(userTokenDTO!=null){
            return userTokenDTO;
        }else{
            return null;
        }
    }
    
}



