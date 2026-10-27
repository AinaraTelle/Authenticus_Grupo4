package es.deusto.sd.authenticus.service;

import java.util.List;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.dto.UserTokenDTO;
import es.deusto.sd.authenticus.external.*;

@Service
public class UserService {
    private final DataStorageGateway dataStorageGateway;

    private final AtomicLong idGenerator = new AtomicLong(0);

    public UserService(DataStorageGateway dataStorageGateway) {
        this.dataStorageGateway = dataStorageGateway;
    }
    
    public UserDTO createUser(RegisterRequestDTO createUserDTO) {
        boolean passwordValida= validacionPassword(createUserDTO.getPassword());
        if(passwordValida==true){
            UserDTO userDTO = dataStorageGateway.registerUser(createUserDTO);
            return userDTO;

        }else{
            return null;
        }
    }

    public UserTokenDTO login(LoginRequestDTO userLogin){
        return dataStorageGateway.loginUser(userLogin);
        
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
        if(tieneMayus==true &&tieneMinus==true && tieneNumero==true && tieneSimbolo==true && tiene8letras==true){
            valido=true;
        }
        return valido;
    }


    public List<UserDTO> getAllUsersRegistrados(){
        return dataStorageGateway.getAllUsers();
    }

    public String getToken(String email){
        return dataStorageGateway.getToken(email);
    }

    
    public boolean removeUsuarioYCasos(String userEmailDTO) {
        return dataStorageGateway.deleteUser(userEmailDTO);
    }

    public boolean logoutUser(String tokenE){
        return dataStorageGateway.logoutUser(tokenE);

    }


}
