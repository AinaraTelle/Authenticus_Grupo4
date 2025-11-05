package es.deusto.sd.authenticus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.entity.User;

@Service
public class UserService {
    private final Estado estado;
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public UserService(Estado estado){
        this.estado=estado;
    }
    
    public UserDTO createUser(UserDTO userDTO) {    
        
        boolean passwordValida=validacionPassword(userDTO.getPassword());

        if(passwordValida==true){

            User user = new User(idGenerator.incrementAndGet(),
            userDTO.getNombre(),userDTO.getEmail(), 
            userDTO.getPassword(), userDTO.getTel());

            boolean esUsuarioNuevo = verificacionExistenciaUsuario(user);
            if(esUsuarioNuevo==false){
                return null;
            }
            estado.getListUsersLogOut().add(user);//añadirUsuarioNuevoALogout

            return convertToDTO(user);
        }else{
            return null;
        }
    }

    boolean validacionPassword(String password){
        boolean valido=false;

        boolean tieneNumero=false;
        boolean tieneSimbolo=false;
        boolean tieneMayus=false;
        boolean tieneMinus=false;
        boolean tiene8letras=false;

        if(password.length()>=8){
            valido=true;
        }
        
        List<Character> nums = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        List<Character> letrasMinus = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        List<Character> letrasMayus = List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
        List<Character> simbolos = List.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')',  '-', '_', '=', '+', '[', ']', '{', '}', '|',    ';', ':', '\'', '"', ',', '.', '/', '?', '<', '>');

        for (int i = 0; i < password.length(); i++) {
            char p1 = password.charAt(i);
            
            if (nums.contains(p1)) {
                tieneNumero=true; // Encontrado
            }
            if(letrasMayus.contains(p1)){
                tieneMayus=true;
            }
            if(letrasMinus.contains(p1)){
                tieneMinus=true;
            }
            if(simbolos.contains(p1)){
                tieneSimbolo=true;
            }
        }
        if(tieneMayus==true &&tieneMinus==true && tieneNumero==true
        && tieneSimbolo==true && tiene8letras==true){
            valido=true;
        }
        return valido;
    }


    boolean verificacionExistenciaUsuario(User miUser){
        boolean esUsuarioNuevo=true;
        ArrayList<User> usuarios=estado.getListUsersLogOut();
        for(User us1: usuarios){
            
            String s1=us1.getEmail().trim();
            String s2=miUser.getEmail().trim();
            if(s1.equalsIgnoreCase(s2)){
                esUsuarioNuevo=false;
            }
        }

        return esUsuarioNuevo;
    }








    private UserDTO convertToDTO(User User) {
        return new UserDTO(User.getIDUsuario(), 
        User.getNombre(), User.getEmail(),
        User.getPassword(),User.getTel());
    }

    public ArrayList<UserDTO> getAllUsersLogOut(){
        ArrayList<UserDTO> listUsersDTOs = new ArrayList<UserDTO>();
        
        for(User User1: estado.getListUsersLogOut()){
            listUsersDTOs.add(convertToDTO(User1));
        }
        return listUsersDTOs;
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

    public User getUserByToken(String token) {
        for (User user : estado.getMap_UserToken().keySet()) {
            if (estado.getMap_UserToken().get(user).equals(token)) {
                return user;
            }
        }
        return null; 
    }

    public String getTokenByUser(User user) {
        return estado.getMap_UserToken().get(user);
    }

}
