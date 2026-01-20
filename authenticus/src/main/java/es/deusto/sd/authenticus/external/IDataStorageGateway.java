package es.deusto.sd.authenticus.external;

import java.time.LocalDateTime;
import java.util.List;

import es.deusto.sd.authenticus.dto.ArchivoDTO;
import es.deusto.sd.authenticus.dto.CasoDTO;
import es.deusto.sd.authenticus.dto.CreateCasoDTO;
import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.dto.UserTokenDTO;

public interface IDataStorageGateway {

    public UserDTO registerUser(RegisterRequestDTO userRegisterDTO);
    public UserTokenDTO loginUser(LoginRequestDTO userLogin);
    public CasoDTO crearCaso(String token, CreateCasoDTO createcasoDTO);
    public  List<ArchivoDTO> obtenerArchivosCaso(Long idCaso);
    public CasoDTO obtenerCaso(Long idCaso);
    List<UserDTO> getAllUsers();
    boolean eliminarCaso(String token, Long idCaso);
    List<CasoDTO> obtenerCasosEntreFechas(String token, LocalDateTime inicio, LocalDateTime fin);
    void addFilesToCase(String token, Long idCaso, List<ArchivoDTO> nuevosArchivos);
}
