package es.deusto.sd.user_interface.service;

import java.util.ArrayList;
import java.util.Arrays;

import es.deusto.sd.user_interface.dto.CreateArchivoDTO;
import es.deusto.sd.user_interface.dto.CreateCasoDTO;
import es.deusto.sd.user_interface.dto.LoginRequestDTO;
import es.deusto.sd.user_interface.dto.RegisterRequestDTO;

public class JsonUtils {

    public String fromRegistertoJson(RegisterRequestDTO dto) {
        return "{" +
            "\"nombre\":\"" + dto.getNombre() + "\"," +
            "\"email\":\"" + dto.getEmail() + "\"," +
            "\"password\":\"" + dto.getPassword() + "\"," +
            "\"tel\":" + dto.getTel() +
            "}";
    }

    public String fromLogintoJson(LoginRequestDTO dto) {
        return "{" +
            "\"email\":\"" + dto.getEmail() + "\"," +
            "\"password\":\"" + dto.getPassword() +
            "\"}";
    }

    public String fromCreatetoJson(CreateCasoDTO dto) {
        String st="{" +
            "\"titulo\":\"" + dto.getTitulo() + "\"," +
            "\"tipoAnalisis\":\"" + dto.gettipoAnalisisDTO() + "\"," +
            "\"fechaCreacion\":\"" + dto.getFechaCreacion() + "\"," +
            "\"createArchivosDTO\":" + this.fromArchivotoJson(dto.getCreateArchivosDTO()) +
            "}";
        String st2= st.replace("\\", "\\\\");;
        return  st2;
    }

    public String fromArchivotoJson(ArrayList<CreateArchivoDTO> dto) {
        String resp="[";
        for(CreateArchivoDTO a1: dto){
            resp+= "{" +
            "\"nombre\":\"" + a1.getNombre() + "\"," +
            "\"ruta\":\"" + a1.getRuta() +
            "\"},";
        }
        String resultado = resp.substring(0, resp.length() - 1);
        
        return resultado+="]";
    }

    public String fromTokentoJson(String token){
        return "{" +
            "\"token\":\"" + token + 
            "\"}";
    }

    public ArrayList<String> fromStringtoArraylist(String casesStr){
        String[] array= casesStr.split("__");
        return new ArrayList<>(Arrays.asList(array));

    }
        
        

}
