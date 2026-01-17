package es.deusto.sd.gestionbd.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import es.deusto.sd.gestionbd.dto.*;
import es.deusto.sd.gestionbd.dao.*;
import es.deusto.sd.gestionbd.entity.*;
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

        User user = (userRepository.findById(userToken.get().getId())).get();
       
        Caso caso = new Caso(createcasoDTO.getTitulo(), createcasoDTO.getFechaCreacion(), user);
        
        caso.setTipoAnalisis(TipoAnalisis.valueOf(createcasoDTO.getTipoAnalisis().toString()));

        casoRepository.save(caso);
        
        //ANADIR ARCHIVOS
        if (createcasoDTO.getCreateArchivosDTO() != null) {
            for (CreateArchivoDTO createArchDTO : createcasoDTO.getCreateArchivosDTO()) {
                Archivo archivo=new Archivo(createArchDTO.getNombre(),
                createArchDTO.getRuta());

                archivo.setCaso(caso);
                
                archivoRepository.save(archivo);
                caso.getArchivos().add(archivo);
            }
        }
        
        user.getCasos().add(caso);

        ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();

        for(Archivo a1:caso.getArchivos()){
            archivosDTO.add(new ArchivoDTO(a1.getId(),a1.getNombre(),a1.getRuta()));
        }
        
        CasoDTO casoDTO =  new CasoDTO(caso.getIDCaso(),caso.getTitulo(), 
        caso.getFechaCreacion(),archivosDTO);

        casoDTO.setTipoAnalisisDTO(TipoAnalisisDTO.valueOf(caso.getTipoAnalisis().toString()));
        
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
        ArrayList<CasoDTO> arrCasosDTO = new ArrayList<CasoDTO>();

        for (Caso caso : casosDelUsuario) {
            ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();
            for(Archivo a1:caso.getArchivos()){
                archivosDTO.add(new ArchivoDTO(a1.getId(),a1.getNombre(),a1.getRuta()));
            }
            CasoDTO casoDTO =new CasoDTO(caso.getIDCaso(),caso.getTitulo(),
            caso.getFechaCreacion(), archivosDTO);

            casoDTO.setTipoAnalisisDTO(TipoAnalisisDTO.valueOf(caso.getTipoAnalisis().toString()));
            
            arrCasosDTO.add(casoDTO);
        }
    
        return arrCasosDTO;
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
    public void addFilesToCase(String token, Long idCaso, ArrayList<ArchivoDTO> nuevosArchivos)
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

        for (ArchivoDTO archDTO1 : nuevosArchivos) {
            Archivo arch1 = new Archivo(archDTO1.getNombre(), archDTO1.getRuta());
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

        List<Caso> casosDelUsuario =user.get().getCasos();
        
        Optional<Caso> casoAEliminar = casoRepository.findById(idCaso);

        if(casoAEliminar.isPresent()) {
            casosDelUsuario.remove(casoAEliminar.get());
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
