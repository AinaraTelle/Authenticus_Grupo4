package es.deusto.sd.authenticus.external;

import es.deusto.sd.authenticus.dto.*;

public interface IDataStorageGateway {

    public UserDTO registerUser(RegisterRequestDTO userRegisterDTO);
}
