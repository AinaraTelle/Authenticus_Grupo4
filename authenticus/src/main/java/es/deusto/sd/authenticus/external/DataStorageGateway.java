
package es.deusto.sd.authenticus.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.deusto.sd.authenticus.dto.*;

@Component
public class DataStorageGateway implements IDataStorageGateway {

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_user = "http://localhost:8081/usuarios";
    private final String URL_case = "http://localhost:8081/casos";

    @Override
    public UserDTO registerUser(RegisterRequestDTO userRegisterDTO) {
        return restTemplate.postForObject(URL_user+"/register", 
        userRegisterDTO,
         UserDTO.class);
    };

    @Override
    public UserTokenDTO loginUser(LoginRequestDTO userLogin){
        UserTokenDTO userTokenDTO= restTemplate.postForObject(URL_user+"/login", 
        userLogin, UserTokenDTO.class);
        if(userTokenDTO!=null){
            return userTokenDTO;
        }else{
            return null;
        }
    };

    @Override
    public CasoDTO crearCaso(String token, CreateCasoDTO createcasoDTO){
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); // Aquí pasamos el token que recibimos

        HttpEntity<CreateCasoDTO> entidadObjetos = new HttpEntity<>(createcasoDTO, headers);

        ResponseEntity<CasoDTO> casoDTO = restTemplate.exchange(
            URL_case + "/crear",
            HttpMethod.POST,
            entidadObjetos,
            CasoDTO.class
        );

        if(casoDTO!=null){
            return casoDTO.getBody();
        }else{
            return null;
        }
    };
    
}



