package es.deusto.sd.authenticus.service;

import java.util.UUID;


import es.deusto.sd.authenticus.entity.*;
import org.springframework.stereotype.Service;
import es.deusto.sd.authenticus.dto.LoginRequestDTO;

@Service
public class UserService {
    private final Estado estado;

    public UserService(Estado estado){
        this.estado=estado;
    }
    

    public boolean verificacionEmailPassword(LoginRequestDTO userLogIn){
 
        String miEmailUser = userLogIn.getEmail();
        String miPasswordUser= userLogIn.getPassword();
        Boolean valido=false;
        for(User user1: estado.getListUsersLogOut()){

            String emailVerif=user1.getEmail();
            String passwordVerif=user1.getPassword();

            if(miEmailUser.equals(emailVerif) &&
            miPasswordUser.equals(passwordVerif)){
                valido=true;
            }
        }
        if (valido==false){ //NO VÁLIDO
            return valido;
        }else {
            return true;
        }
    }

    public void generacionAsignacionToken(User usuarioLogIn){
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        estado.getMap_UserToken().put(usuarioLogIn, token);
    }

    public void actualizacionListas(User usuarioLogIn){
        estado.getListUsersLogIn().add(usuarioLogIn);
        estado.getListUsersLogOut().remove(usuarioLogIn);

    }

    public User busquedaUsuarioValido(LoginRequestDTO userLogin){
        User usuarioLogin = null;
        for(User user1: estado.getListUsersLogOut()){

            if(user1.getEmail().equals(userLogin.getEmail()) &&
            user1.getPassword().equals(userLogin.getPassword())){
                usuarioLogin=user1;
            }
        }
        return usuarioLogin;

    }

}
