package es.deusto.sd.authenticus.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.sd.authenticus.dto.ArchivoDTO;
import es.deusto.sd.authenticus.dto.CasoDTO;
import es.deusto.sd.authenticus.dto.CreateCasoDTO;
import es.deusto.sd.authenticus.dto.ResultadosDTO;
import es.deusto.sd.authenticus.dto.TipoAnalisisDTO;
import es.deusto.sd.authenticus.external.DataStorageGateway;
import es.deusto.sd.authenticus.external.ProccesSocketClient;
import jakarta.transaction.Transactional;

@Service
public class CaseService {
    private final DataStorageGateway dataStorageGateway;
    private ProccesSocketClient cliente = new ProccesSocketClient("127.0.0.1", 5000);
    
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

    @Transactional
    public List<CasoDTO> obtenerCasosDeUsuario(String token, int limite) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return dataStorageGateway.obtenerCasosDeUsuario(token, limite);
    }

    @Transactional
        public List<CasoDTO> obtenerCasosDeUsuarioEntreFechas(String token, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Llamamos al gateway para pedir los datos al servidor 8081
        return dataStorageGateway.obtenerCasosEntreFechas(token, fechaInicio, fechaFin);
    }
    

//  ANADIR ARCHIVOS
    @Transactional
    public boolean addFilesToCase(String token, Long idCaso, ArrayList<ArchivoDTO> nuevosArchivos)
        throws IllegalAccessException, IllegalArgumentException {

         boolean a =dataStorageGateway.addFilesToCase(token, idCaso, nuevosArchivos);
        return a;
    }

    //Eliminar caso
    @Transactional
    public boolean eliminarCaso(String token, Long idCaso) throws IllegalAccessException { 

        boolean exito = dataStorageGateway.eliminarCaso(token, idCaso);
        if (!exito) {
            return false;
        }
        return true;
    }


    public List<ResultadosDTO> mostrarResultados(Long idCaso){
       
        List<ResultadosDTO> listResultados = new ArrayList<>();
        CasoDTO caso_archivos = dataStorageGateway.obtenerCaso(idCaso);
        List<ArchivoDTO> listArchivos = caso_archivos.getArchivosDTO();

        for(ArchivoDTO a1:listArchivos){
            String respuesta = cliente.enviarAnalisis(caso_archivos.getTipoAnalisisDTO().name(),
            a1.getIdArchivo());

            if (!respuesta.startsWith("ERROR")) {
                double valor = Double.parseDouble(respuesta);
                listResultados.add(new ResultadosDTO(a1.getIdArchivo(), valor,
                TipoAnalisisDTO.valueOf(caso_archivos.getTipoAnalisisDTO().name())));
            }
            
        }

        return listResultados;


        //inicializar sockets
        //por cada archivo, ejecutar "enviar analisis" de ProccessSocketClient
        // aignar valor aletario
        //generar un resustadosDTO y añadir a la lista de listResultados

        //al acabar, devolver listResultados

        

    };




}
