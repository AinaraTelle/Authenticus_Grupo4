package es.deusto.sd.authenticus.service;
import java.util.ArrayList;
// import java.util.HashMap;

import org.springframework.stereotype.Service;

// import es.deusto.sd.authenticus.entity.Caso;
import es.deusto.sd.authenticus.entity.Usuario;
import es.deusto.sd.authenticus.dto.UsuarioDTO;

@Service
public class Estado {
    private final ArrayList<Usuario> listUsersLogIn = new ArrayList<Usuario>();
    // private final ArrayList<Usuario> listUsersLogOut = new ArrayList<Usuario>();

    // private final ArrayList< HashMap< Usuario,String>> listMap_UserToken= new ArrayList< HashMap< Usuario,String>>();
    // private final ArrayList< HashMap< Usuario,ArrayList<Caso>>> listMap_UserCases= new ArrayList< HashMap< Usuario,ArrayList<Caso>>>();

    

    public ArrayList<UsuarioDTO> getAllUsuarios(){
        ArrayList<UsuarioDTO> listUsuariosDTOs = new ArrayList<UsuarioDTO>();
        
        for(Usuario usuario1: listUsersLogIn){
            listUsuariosDTOs.add(convertToDTO(usuario1));
        }
        return listUsuariosDTOs;
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getIDUsuario(), 
        usuario.getNombre(), usuario.getEmail(),
        usuario.getPassword(),usuario.getTel());
    }

}