
package es.deusto.sd.authenticus.external;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    public List<UserDTO> getAllUsers() {
        try {
            ResponseEntity<UserDTO[]> response = restTemplate.getForEntity(URL_user, UserDTO[].class);
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error al conectar con GestionBD para obtener usuarios: " + e.getMessage());
            return null;
        }
    }

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

    @Override
    public boolean eliminarCaso(String token, Long idCaso) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // Llamamos a GestionBD (puerto 8081)
            ResponseEntity<Boolean> response = restTemplate.exchange(
                URL_case + "/eliminar/" + idCaso,
                HttpMethod.DELETE,
                entity,
                Boolean.class
            );

            return response.getBody() != null && response.getBody();
        } catch (Exception e) {
            System.out.println("Error al eliminar caso en el gateway: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<CasoDTO> obtenerCasosEntreFechas(String token, LocalDateTime inicio, LocalDateTime fin) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // Construimos la URL con los parámetros: ?inicio=...&fin=...
            String urlTemplate = UriComponentsBuilder.fromHttpUrl(URL_case + "/mis-casos-fechas")
                    .queryParam("inicio", inicio.toString())
                    .queryParam("fin", fin.toString())
                    .encode()
                    .toUriString();

            ResponseEntity<CasoDTO[]> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                entity,
                CasoDTO[].class
            );

            return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al buscar casos por fecha: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void addFilesToCase(String token, Long idCaso, List<ArchivoDTO> nuevosArchivos) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            
            // El cuerpo de la petición contiene la lista de archivos
            HttpEntity<List<ArchivoDTO>> entidad = new HttpEntity<>(nuevosArchivos, headers);

            // Construimos la URL con el parámetro idCaso (query param como en tu controller externo)
            String url = UriComponentsBuilder.fromHttpUrl(URL_case + "/add-files")
                    .queryParam("idCaso", idCaso)
                    .toUriString();

            restTemplate.exchange(url, HttpMethod.PUT, entidad, String.class);
        } catch (Exception e) {
            System.out.println("Error al añadir archivos externamente: " + e.getMessage());
        }
    }

}




