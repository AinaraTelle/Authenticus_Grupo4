package es.deusto.sd.authenticus.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import es.deusto.sd.authenticus.dao.*;
import es.deusto.sd.authenticus.dto.*;
import es.deusto.sd.authenticus.entity.*;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@Service
public class CaseService {
    private final CasoRepository casoRepository;
    private final AtomicLong idGenerator = new AtomicLong(0);

    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;
    private final ArchivoRepository archivoRepository;

    public CaseService(ArchivoRepository archivoRepository,CasoRepository casoRepository, UserTokenRepository userTokenRepository,UserRepository userRepository) {
        this.casoRepository=casoRepository;
        this.userTokenRepository=userTokenRepository;
        this.userRepository=userRepository;
        this.archivoRepository=archivoRepository;
    }
      
    @Transactional
    public CasoDTO crearCaso(String token, CreateCasoDTO casoDTO){
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        if(!userToken.isPresent()){
            throw new RuntimeException("Usuario no autenticado o token inválido.");
        }

        Optional<User> userO = userRepository.findById(userToken.get().getId());
        User user = userO.get();
        Caso caso= new Caso(casoDTO.getTitulo(),casoDTO.getTipoAnalisis(), casoDTO.getFechaCreacion(),user);
        
        casoRepository.save(caso);
        
        //ANADIR ARCHIVOS
        if (casoDTO.getArchivos() != null) {
            for (Archivo arch1 : casoDTO.getArchivos()) {
                caso.addArchivo(arch1);
                archivoRepository.save(arch1);
                // arch1.setIDArchivo(idGenerator.incrementAndGet());
            }
        }
        
        user.getCasos().add(caso);
        
        return new CasoDTO(caso.getId(),caso.getTitulo(),caso.getTipoAnalisis(),caso.getFechaCreacion(),caso.getArchivos());
    }

//     public ArrayList<CasoDTO> obtenerCasosDeUsuario(String token) {
//         if(token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }
//         User usuario = userService.getUserByToken(token);
    
//         if (usuario == null) {
//             throw new RuntimeException("Usuario no autenticado o token inválido.");
//         }
    
        
//         ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
    
        
//         ArrayList<CasoDTO> casosDTO = new ArrayList<>();
//         for (Caso caso : casosDelUsuario) {
//             casosDTO.add(new CasoDTO(caso.getIDCaso(),caso.getTitulo(),caso.getTipoAnalisis(),caso.getFechaCreacion(), caso.getArchivos()));
//         }
    
//         return casosDTO;
//     }

//     public ArrayList<CasoDTO> obtenerCasosDeUsuarioEntreFechas(String token, LocalDateTime FechaInicio, LocalDateTime FechaFin){
//         if(token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }
//         User usuario = userService.getUserByToken(token);
//         if (usuario == null) {
//             throw new RuntimeException("Usuario no autenticado o token inválido.");
//         }
    
        
//         ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
//         List<Caso> filtrados = casosDelUsuario.stream()
//         .filter(c -> c.getFechaCreacion() != null)
//         .filter(c -> !c.getFechaCreacion().isBefore(FechaInicio)) 
//         .filter(c -> !c.getFechaCreacion().isAfter(FechaFin))     
//         .toList();
//         ArrayList<CasoDTO> casosDTO= new ArrayList<>();
//         for (Caso caso : filtrados) {
//             casosDTO.add(new CasoDTO(caso.getIDCaso(),caso.getTitulo(),caso.getTipoAnalisis(),caso.getFechaCreacion(), caso.getArchivos()));
//         }
    
        
//         return casosDTO;
//     }


//     //  ANADIR ARCHIVOS

//     public void addFilesToCase(String token, int idCaso, ArrayList<Archivo> nuevosArchivos)
//         throws IllegalAccessException, IllegalArgumentException {

//         if (token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }

//         User usuario = userService.getUserByToken(token);
//         if (usuario == null) {
//             throw new IllegalAccessException("Usuario no autenticado o token inválido.");
//         }

//         ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());

//         Caso casoEncontrado = null;
//         for (Caso c : casosDelUsuario) {
//             if (c.getIDCaso() == idCaso) {
//                 casoEncontrado = c;
//                 break;
//             }
//         }

//         if (casoEncontrado == null) {
//             throw new IllegalArgumentException("Caso no encontrado para este usuario.");
//         }

//         // Añadir archivos
//         for (Archivo arch1 : nuevosArchivos) {
//             casoEncontrado.addArchivo(arch1);
//         }
//     }

//     //Eliminar caso
//     public boolean eliminarCaso(String token, int idCaso) { 
//         if(token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }
//         User usuario = userService.getUserByToken(token);
//         if(usuario == null) {
//             throw new RuntimeException("Usuario no autenticado o token inválido.");
//         }

//         ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
//         Caso casoAEliminar = null;
//         for(Caso c : casosDelUsuario) {
//             if(c.getIDCaso() == idCaso) {
//                 casoAEliminar = c;
//                 break;
//             }
//         }
        

//         if(casoAEliminar != null) {
//             casosDelUsuario.remove(casoAEliminar);
//             estado.getMap_UserCases().put(usuario, casosDelUsuario);
//             return true;
//         } else {
//             return false;
//         }
//     }

//     public ResultadosDTO mostrarResultados(int idUsuario, int idCaso){
//         Caso caso=buscaCaso(idUsuario, idCaso);

//         return new ResultadosDTO(caso.getIDCaso(), caso.getTitulo(),
//         caso.getTipoAnalisis() , caso.getFechaCreacion(), caso.getArchivos());
//     };


//     public Caso buscaCaso(int idUsuario, int idCaso){
//         User usuario = null;

//         for(User us1:estado.getListUsersLogIn()){
//             if(us1.getIDUsuario()==idUsuario){
//                 usuario=us1;
//             }
//         }
        

//         ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
//         Caso casoEncontrado = null;
//         for(Caso c : casosDelUsuario) {
//             if(c.getIDCaso() == idCaso) {
//                 casoEncontrado = c;
//                 break;
//             }
//         }
//         return casoEncontrado;
//     }

}
