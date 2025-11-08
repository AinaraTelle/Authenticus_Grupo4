package es.deusto.sd.authenticus.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.sd.authenticus.dto.CasoDTO;
import es.deusto.sd.authenticus.entity.Archivo;
import es.deusto.sd.authenticus.entity.Caso;
import es.deusto.sd.authenticus.entity.User;
import java.time.LocalDateTime;
@Service
public class CaseService {
    private final Estado estado;
    private UserService userService;// Para utilizar la función de convertir un token en user

    public CaseService(Estado estado, UserService userService) {
        this.estado = estado;
        this.userService=userService;
    }

    public CasoDTO crearCaso(String token, CasoDTO casoDTO){
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        User usuario=userService.getUserByToken(token);
        if(usuario==null){
            throw new RuntimeException("Usuario no autenticado o token inválido.");
        }
        Caso caso= new Caso(casoDTO.getIDCaso(), casoDTO.getTitulo(), casoDTO.getTipoAnalisis(), casoDTO.getFechaCreacion());
        
        //ANADIR ARCHIVOS
        if (casoDTO.getArchivos() != null) {
            for (String ruta : casoDTO.getArchivos()) {
                caso.addArchivo(new Archivo(ruta, ruta));
            }
        }
        
        ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
        casosDelUsuario.add(caso);
        estado.getMap_UserCases().put(usuario, casosDelUsuario);
        
        return new CasoDTO(caso.getIDCaso(),caso.getTitulo(),caso.getTipoAnalisis(),caso.getFechaCreacion(),caso.getArchivos().stream().map(Archivo::getRuta).toList());
    }

    public ArrayList<CasoDTO> obtenerCasosDeUsuario(String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        User usuario = userService.getUserByToken(token);
    
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado o token inválido.");
        }
    
        
        ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
    
        
        ArrayList<CasoDTO> casosDTO = new ArrayList<>();
        for (Caso caso : casosDelUsuario) {
            casosDTO.add(new CasoDTO(caso.getIDCaso(),caso.getTitulo(),caso.getTipoAnalisis(),caso.getFechaCreacion(), caso.getArchivos().stream().map(Archivo::getRuta).toList()));
        }
    
        return casosDTO;
    }

    public ArrayList<CasoDTO> obtenerCasosDeUsuarioEntreFechas(String token, LocalDateTime FechaInicio, LocalDateTime FechaFin){
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        User usuario = userService.getUserByToken(token);
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado o token inválido.");
        }
    
        
        ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
        List<Caso> filtrados = casosDelUsuario.stream()
        .filter(c -> c.getFechaCreacion() != null)
        .filter(c -> !c.getFechaCreacion().isBefore(FechaInicio)) 
        .filter(c -> !c.getFechaCreacion().isAfter(FechaFin))     
        .toList();
        ArrayList<CasoDTO> casosDTO= new ArrayList<>();
        for (Caso caso : filtrados) {
            casosDTO.add(new CasoDTO(caso.getIDCaso(),caso.getTitulo(),caso.getTipoAnalisis(),caso.getFechaCreacion(), caso.getArchivos().stream().map(Archivo::getRuta).toList()));
        }
    
        
        return casosDTO;
    }


    //  ANADIR ARCHIVOS

    public void addFilesToCase(String token, int idCaso, ArrayList<String> nuevosArchivos)
        throws IllegalAccessException, IllegalArgumentException {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        User usuario = userService.getUserByToken(token);
        if (usuario == null) {
            throw new IllegalAccessException("Usuario no autenticado o token inválido.");
        }

        ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());

        Caso casoEncontrado = null;
        for (Caso c : casosDelUsuario) {
            if (c.getIDCaso() == idCaso) {
                casoEncontrado = c;
                break;
            }
        }

        if (casoEncontrado == null) {
            throw new IllegalArgumentException("Caso no encontrado para este usuario.");
        }

        // Añadir archivos
        for (String ruta : nuevosArchivos) {
            casoEncontrado.addArchivo(new Archivo(ruta, ruta));
        }
    }



}
