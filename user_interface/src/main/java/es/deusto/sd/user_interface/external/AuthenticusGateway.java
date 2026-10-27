package es.deusto.sd.user_interface.external;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import es.deusto.sd.user_interface.dto.CreateCasoDTO;
import es.deusto.sd.user_interface.dto.LoginRequestDTO;
import es.deusto.sd.user_interface.dto.RegisterRequestDTO;
import es.deusto.sd.user_interface.service.JsonUtils;

public class AuthenticusGateway {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String BASE_USERS = "http://localhost:8080/usuarios";
    private final String BASE_CASES = "http://localhost:8080/casos"; // Puerto de Authenticus
    private final JsonUtils json = new JsonUtils();

    public boolean signUp(RegisterRequestDTO registerRequestDTO) {
        try {
            // 1. Preparamos los Headers con el Token

            String url = BASE_USERS + "/register";
            String jsonSignup = json.fromRegistertoJson(registerRequestDTO);

            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)) // URL de Authenticus
            .POST(HttpRequest.BodyPublishers.ofString(jsonSignup))
            .header("Content-Type", "application/json") // Aunque sea simple, esto ayuda al servidor
            .build();

            HttpResponse<String> response= httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 201){
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error en la comunicación con Authenticus: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean login(LoginRequestDTO loginRequest){
        try {
            String url = BASE_USERS + "/login";
            String jsonLogin = json.fromLogintoJson(loginRequest);

            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)) // URL de Authenticus
            .POST(HttpRequest.BodyPublishers.ofString(jsonLogin))
            .header("Content-Type", "application/json") // Aunque sea simple, esto ayuda al servidor
            .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al iniciar sesión");
            e.printStackTrace();
            return false;
        }
    }

    public boolean createCase(CreateCasoDTO createCasoDTO,String userEmail){
        try {
            String token=this.getToken(userEmail);

            String url = BASE_CASES + "/crear";
            String jsonCreateCase = json.fromCreatetoJson(createCasoDTO);

            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)) // URL de Authenticus
            .POST(HttpRequest.BodyPublishers.ofString(jsonCreateCase))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token) // Aunque sea simple, esto ayuda al servidor
            .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 201){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al iniciar sesión");
            e.printStackTrace();
            return false;
        }
    }

    public String getToken(String email){
        try {
            String url = BASE_USERS + "/getToken/"+email;
        
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)) // URL de Authenticus
                .GET()
                .build();
    
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body(); // Aquí vendría el token en texto plano
            } else {
                System.err.println("Error obteniendo token: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean logout(String token){
        try {
            String url = BASE_USERS + "/logout";
            String jsonLogout = json.fromTokentoJson(token);

            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)) // URL de Authenticus
            .POST(HttpRequest.BodyPublishers.ofString(jsonLogout))
            .header("Content-Type", "application/json") // Aunque sea simple, esto ayuda al servidor
            .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return true; 
            } else {
                System.err.println("Error obteniendo token: " + response.statusCode());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> getCases_str(String userEmail, int numberCases, String token){
    try {
        String url = BASE_CASES + "/mis-casos";
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)) // URL de Authenticus
            .GET()
            .header("Authorization", token)
            .build();

        HttpResponse<String> responseSTR = httpClient.send(request, 
            HttpResponse.BodyHandlers.ofString());


        if (responseSTR.statusCode() == 200) {
            return json.fromStringtoArraylist(responseSTR.body()); // Aquí vendría el token en texto plano
        
        } else {
            System.err.println("Error obteniendo casos: " + responseSTR.statusCode());
            return null;
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        return null;
    }
        }
}
