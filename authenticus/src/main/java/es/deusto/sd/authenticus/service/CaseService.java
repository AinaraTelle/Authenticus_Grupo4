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
    public CasoDTO crearCaso(String token, CreateCasoDTO createcasoDTO){
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        if(!userToken.isPresent()){
            throw new RuntimeException("Usuario no autenticado o token inválido.");
        }

        Optional<User> userO = userRepository.findById(userToken.get().getId());
        User user = userO.get();
       
        Caso caso= new Caso(createcasoDTO.getTitulo(), createcasoDTO.getFechaCreacion(),user);
        
        caso.setTipoAnalisis(TipoAnalisis.valueOf(createcasoDTO.getTipoAnalisis().toString()));

        casoRepository.save(caso);
        
        //ANADIR ARCHIVOS
        if (createcasoDTO.getArchivoDTOs() != null) {
            for (ArchivoDTO archDTO : createcasoDTO.getArchivoDTOs()) {
                Archivo archivo=new Archivo(archDTO.getNombre(),archDTO.getRuta());
                archivo.setCaso(caso);
                
                archivoRepository.save(archivo);
                caso.getArchivos().add(archivo);
                
                // arch1.setIDArchivo(idGenerator.incrementAndGet());
            }
        }
        
        user.getCasos().add(caso);

        ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();

        for(Archivo a1:caso.getArchivos()){
            archivosDTO.add(new ArchivoDTO(a1.getId(),a1.getNombre(),a1.getRuta()));
        }
        
        CasoDTO casoDTO =  new CasoDTO(caso.getIDCaso(),caso.getTitulo(),
        caso.getFechaCreacion(),archivosDTO);

        casoDTO.setTipoAnalisis(TipoAnalisisDTO.valueOf(casoDTO.getTipoAnalisis().toString()));
        
        return  casoDTO;
    }

    @Transactional
    public ArrayList<CasoDTO> obtenerCasosDeUsuario(String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
    
        if (!userToken.isPresent()) {
            throw new RuntimeException("Usuario no autenticado o token inválido.");
        }
        User user =userRepository.findById(userToken.get().getId()).get();
        
        List<Caso> casosDelUsuario = casoRepository.findByUsuario(user);
        ArrayList<CasoDTO> casosDTO = new ArrayList<CasoDTO>();
        


        for (Caso caso : casosDelUsuario) {
            ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();
            for(Archivo a1:caso.getArchivos()){
                archivosDTO.add(new ArchivoDTO(a1.getId(),a1.getNombre(),a1.getRuta()));
            }
            casosDTO.add(new CasoDTO(caso.getIDCaso(),caso.getTitulo(),
            caso.getFechaCreacion(), archivosDTO));
        }
    
        return casosDTO;
    }

    @Transactional
    public ArrayList<CasoDTO> obtenerCasosDeUsuarioEntreFechas(String token, LocalDateTime FechaInicio, LocalDateTime FechaFin) throws IllegalAccessException{
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        if (!userToken.isPresent()) {
            throw new IllegalAccessException("Usuario no autenticado o token inválido.");
        }
    
        Optional<User> user = userRepository.findById(userToken.get().getId());
        List <Caso> casosDelUsuario =user.get().getCasos();

        // ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
        List<Caso> filtrados = casosDelUsuario.stream()
        .filter(c -> c.getFechaCreacion() != null)
        .filter(c -> !c.getFechaCreacion().isBefore(FechaInicio)) 
        .filter(c -> !c.getFechaCreacion().isAfter(FechaFin))     
        .toList();

        ArrayList<CasoDTO> casosDTO= new ArrayList<>();
        for (Caso caso : filtrados) {
            ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();
            for(Archivo a1:caso.getArchivos()){
                archivosDTO.add(new ArchivoDTO(a1.getId(),
                a1.getNombre(),a1.getRuta()));
            }

            casosDTO.add(new CasoDTO(caso.getIDCaso(),caso.getTitulo(),
            caso.getFechaCreacion(), archivosDTO));
        }
    
        
        return casosDTO;
    }


//     //  ANADIR ARCHIVOS
    @Transactional
    public void addFilesToCase(String token, Long idCaso, ArrayList<Archivo> nuevosArchivos)
        throws IllegalAccessException, IllegalArgumentException {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        if (!userToken.isPresent()) {
            throw new IllegalAccessException("Usuario no autenticado o token inválido.");
        }

        Optional <Caso> casoEncontrado = casoRepository.findById(idCaso);

        if (!casoEncontrado.isPresent()) {
            throw new IllegalArgumentException("Caso no encontrado para este usuario.");
        }

        for (Archivo arch1 : nuevosArchivos) {
            arch1.setCaso(casoEncontrado.get());
            archivoRepository.save(arch1);
            casoEncontrado.get().getArchivos().add(arch1);
            

        }
    }

//     //Eliminar caso
    @Transactional
    public boolean eliminarCaso(String token, Long idCaso) throws IllegalAccessException { 
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        if (!userToken.isPresent()) {
            throw new IllegalAccessException("Usuario no autenticado o token inválido.");
        }

        Optional<User> user = userRepository.findById(userToken.get().getId());

        List <Caso> casosDelUsuario =user.get().getCasos();
        
        Optional<Caso> casoAEliminar = casoRepository.findById(idCaso);

        if(casoAEliminar.isPresent()) {
            casosDelUsuario.remove(casoAEliminar);
            casoRepository.deleteById(casoAEliminar.get().getIDCaso());
            return true;
        } else {
            return false;
        }
    }

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
