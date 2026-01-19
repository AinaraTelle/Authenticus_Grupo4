package es.deusto.sd.authenticus.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.sd.authenticus.dto.ArchivoDTO;
import es.deusto.sd.authenticus.dto.CasoDTO;
import es.deusto.sd.authenticus.dto.CreateCasoDTO;
import es.deusto.sd.authenticus.dto.ResultadosDTO;
import es.deusto.sd.authenticus.external.DataStorageGateway;
import es.deusto.sd.authenticus.external.ProccesSocketClient;
import jakarta.transaction.Transactional;

@Service
public class CaseService {
    private final DataStorageGateway dataStorageGateway;
//     private final CasoRepository casoRepository;
//     private final AtomicLong idGenerator = new AtomicLong(0);

//     private final UserTokenRepository userTokenRepository;
//     private final UserRepository userRepository;
//     private final ArchivoRepository archivoRepository;

//     public CaseService(ArchivoRepository archivoRepository,CasoRepository casoRepository, UserTokenRepository userTokenRepository,UserRepository userRepository) {
//         this.casoRepository=casoRepository;
//         this.userTokenRepository=userTokenRepository;
//         this.userRepository=userRepository;
//         this.archivoRepository=archivoRepository;
//     }
    public CaseService(DataStorageGateway dataStorageGateway) {
        this.dataStorageGateway=dataStorageGateway;
    }
      
    @Transactional
    public CasoDTO crearCaso(String token, CreateCasoDTO createcasoDTO){
        
        CasoDTO casoDTO = dataStorageGateway.crearCaso(token, createcasoDTO);
        if(casoDTO!=null){
            return casoDTO;
        }else{
            return null;
        }
    }

//     @Transactional
//     public ArrayList<CasoDTO> obtenerCasosDeUsuario(String token) {
//         if(token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }
//         Optional<UserToken> userToken = userTokenRepository.findByToken(token);
    
//         if (!userToken.isPresent()) {
//             throw new RuntimeException("Usuario no autenticado o token inválido.");
//         }
//         User user =userRepository.findById(userToken.get().getId()).get();
        
//         List<Caso> casosDelUsuario = casoRepository.findByUsuario(user);
//         ArrayList<CasoDTO> arrCasosDTO = new ArrayList<CasoDTO>();

//         for (Caso caso : casosDelUsuario) {
//             ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();
//             for(Archivo a1:caso.getArchivos()){
//                 archivosDTO.add(new ArchivoDTO(a1.getId(),a1.getNombre(),a1.getRuta()));
//             }
//             CasoDTO casoDTO =new CasoDTO(caso.getIDCaso(),caso.getTitulo(),
//             caso.getFechaCreacion(), archivosDTO);

//             casoDTO.setTipoAnalisisDTO(TipoAnalisisDTO.valueOf(caso.getTipoAnalisis().toString()));
            
//             arrCasosDTO.add(casoDTO);
//         }
    
//         return arrCasosDTO;
//     }

//     @Transactional
//     public ArrayList<CasoDTO> obtenerCasosDeUsuarioEntreFechas(String token, LocalDateTime FechaInicio, LocalDateTime FechaFin) throws IllegalAccessException{
//         if(token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }

//         Optional<UserToken> userToken = userTokenRepository.findByToken(token);
//         if (!userToken.isPresent()) {
//             throw new IllegalAccessException("Usuario no autenticado o token inválido.");
//         }
    
//         Optional<User> user = userRepository.findById(userToken.get().getId());
//         List <Caso> casosDelUsuario =user.get().getCasos();

//         // ArrayList<Caso> casosDelUsuario = estado.getMap_UserCases().getOrDefault(usuario, new ArrayList<>());
//         List<Caso> filtrados = casosDelUsuario.stream()
//         .filter(c -> c.getFechaCreacion() != null)
//         .filter(c -> !c.getFechaCreacion().isBefore(FechaInicio)) 
//         .filter(c -> !c.getFechaCreacion().isAfter(FechaFin))     
//         .toList();

//         ArrayList<CasoDTO> casosDTO= new ArrayList<>();
//         for (Caso caso : filtrados) {
//             ArrayList<ArchivoDTO> archivosDTO = new ArrayList<>();
//             for(Archivo a1:caso.getArchivos()){
//                 archivosDTO.add(new ArchivoDTO(a1.getId(),
//                 a1.getNombre(),a1.getRuta()));
//             }

//             casosDTO.add(new CasoDTO(caso.getIDCaso(),caso.getTitulo(),
//             caso.getFechaCreacion(), archivosDTO));
//         }
    
        
//         return casosDTO;
//     }


// //     //  ANADIR ARCHIVOS
//     @Transactional
//     public void addFilesToCase(String token, Long idCaso, ArrayList<ArchivoDTO> nuevosArchivos)
//         throws IllegalAccessException, IllegalArgumentException {

//         if (token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }

//         Optional<UserToken> userToken = userTokenRepository.findByToken(token);
//         if (!userToken.isPresent()) {
//             throw new IllegalAccessException("Usuario no autenticado o token inválido.");
//         }

//         Optional <Caso> casoEncontrado = casoRepository.findById(idCaso);

//         if (!casoEncontrado.isPresent()) {
//             throw new IllegalArgumentException("Caso no encontrado para este usuario.");
//         }

//         for (ArchivoDTO archDTO1 : nuevosArchivos) {
//             Archivo arch1 = new Archivo(archDTO1.getNombre(), archDTO1.getRuta());
//             arch1.setCaso(casoEncontrado.get());
//             archivoRepository.save(arch1);
//             casoEncontrado.get().getArchivos().add(arch1);
//         }
//     }

// //     //Eliminar caso
//     @Transactional
//     public boolean eliminarCaso(String token, Long idCaso) throws IllegalAccessException { 
//         if(token.startsWith("Bearer ")) {
//             token = token.substring(7);
//         }
//         Optional<UserToken> userToken = userTokenRepository.findByToken(token);
//         if (!userToken.isPresent()) {
//             throw new IllegalAccessException("Usuario no autenticado o token inválido.");
//         }

//         Optional<User> user = userRepository.findById(userToken.get().getId());

//         List<Caso> casosDelUsuario =user.get().getCasos();
        
//         Optional<Caso> casoAEliminar = casoRepository.findById(idCaso);

//         if(casoAEliminar.isPresent()) {
//             casosDelUsuario.remove(casoAEliminar.get());
//             casoRepository.deleteById(casoAEliminar.get().getIDCaso());
//             return true;
//         } else {
//             return false;
//         }
//     }

    public List<ResultadosDTO> mostrarResultados(Long idCaso){
        List<ArchivoDTO> listArchivos = dataStorageGateway.obtenerArchivosCaso(idCaso);
        List<ResultadosDTO> listResultados = new ArrayList<>();
        CasoDTO caso_archivos= dataStorageGateway.obtenerCaso(idCaso);

        //empezar bucle for de archivos
        for (ArchivoDTO archivo : listArchivos) {
            ProccesSocketClient socketcliente = new ProccesSocketClient("127.0.0.1", 5000);
            String resultado_string=socketcliente.enviarAnalisis(caso_archivos.getTipoAnalisis().name(), idCaso);
            int id_archivo_int=archivo.getIDArchivo().intValue();// Se nos pide un int en parametro resultados y tenemos long
            ResultadosDTO resultado= new ResultadosDTO(id_archivo_int,Double.parseDouble(resultado_string),caso_archivos.getTipoAnalisis());
            listResultados.add(resultado);   
        }
        return listResultados;
        //inicializar sockets
        //por cada archivo, ejecutar "enviar analisis" de ProccessSocketClient
        // aignar valor aletario
        //generar un resustadosDTO y añadir a la lista de listResultados

        //al acabar, devolver listResultados

        

    };


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
