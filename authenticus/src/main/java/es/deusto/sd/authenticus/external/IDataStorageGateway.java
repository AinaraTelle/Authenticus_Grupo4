package es.deusto.sd.authenticus.external;

import java.util.List;

import es.deusto.sd.authenticus.dto.*;

public interface IDataStorageGateway {

    public UserDTO registerUser(RegisterRequestDTO userRegisterDTO);
    public UserTokenDTO loginUser(LoginRequestDTO userLogin);
    public CasoDTO crearCaso(String token, CreateCasoDTO createcasoDTO);
    public  List<ArchivoDTO> obtenerArchivosCaso(Long idCaso);
}
