
package es.deusto.sd.authenticus.external;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.deusto.sd.authenticus.dto.ArchivoDTO;
import es.deusto.sd.authenticus.dto.CasoDTO;
import es.deusto.sd.authenticus.dto.CreateCasoDTO;
import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.dto.UserTokenDTO;

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
    
    @Override
    public List<ArchivoDTO> obtenerArchivosCaso(Long idCaso){

        try{        
            ResponseEntity<ArchivoDTO[]> response = restTemplate.exchange(
                URL_case+"/buscarArchivos/{idCaso}",
                HttpMethod.GET, // O POST, según tu controlador de BD
                null,
                ArchivoDTO[].class,
                idCaso );// <--- Aquí es donde se asigna el valor al {id} de la URL

            return Arrays.asList(response.getBody());

        }catch (Exception e) {
            System.out.println("Error al cargar los archivos");
            return null;
        }
    }
    @Override
    public CasoDTO obtenerCaso(Long idCaso) {
        try {
            ResponseEntity<CasoDTO> response = restTemplate.exchange(
                    URL_case + "/{idCaso}",
                    HttpMethod.GET,
                    null,
                    CasoDTO.class,
                    idCaso
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error al obtener el caso con id " + idCaso + ": " + e.getMessage());
            return null;
        }
}
}



