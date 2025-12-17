package es.deusto.sd.authenticus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import es.deusto.sd.authenticus.dao.UserRepository;
import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.entity.User;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AtomicLong idGenerator = new AtomicLong(0);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserDTO createUser(RegisterRequestDTO userDTO) {    
        
        boolean passwordValida=validacionPassword(userDTO.getPassword());

        if(passwordValida==true){

            User user = new User(idGenerator.incrementAndGet(),
            userDTO.getNombre(),userDTO.getEmail(), 
            userDTO.getPassword(), userDTO.getTel());

           if (userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {
                return null; // O lanza una excepción personalizada
            }

            // boolean esUsuarioNuevo = verificacionExistenciaUsuario(user);
            // if(esUsuarioNuevo==false){
            //     return null;
            // }

            user.setIDUsuario(null);
            userRepository.save(user);
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


    public User busquedaEmailPassword(LoginRequestDTO miUser){
        Optional<User> user = userRepository.findByEmailAndPassword(miUser.getEmail(),miUser.getPassword());
        if (user.isPresent()) {
            return user.get();
        } else {
            return null; 
        }
    }

    private UserDTO convertToDTO(User User) {
        return new UserDTO(User.getIDUsuario(), 
        User.getNombre(), User.getEmail(),
        User.getPassword(),User.getTel());
    }

    public ArrayList<UserDTO> getAllUsersRegistrados(){
        ArrayList<UserDTO> listUsersDTOs = new ArrayList<UserDTO>();
        
        for(User User1: userRepository.findAll()){
            listUsersDTOs.add(convertToDTO(User1));
        }
        return listUsersDTOs;
    }

    public void generacionAsignacionToken(User usuarioLogIn){
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        User userDB = userRepository.findById(usuarioLogIn.getIDUsuario()).
        orElseThrow(() -> new RuntimeException("No encontrado"));
        userDB.setToken(token);
        usuarioLogIn.setToken(token);
        userRepository.save(userDB);
    }

    public void ponerLoginATrue(User usuarioLogIn){
        User userDB = userRepository.findById(usuarioLogIn.getIDUsuario()).
        orElseThrow(() -> new RuntimeException("No encontrado"));
        userDB.setLogin(true);
        usuarioLogIn.setLogin(true);
        userRepository.save(userDB);
    }

    public User getUserByToken(String token) {

        for (User us1 : userRepository.findAll()) {
            if(token == us1.getToken()){
                return us1;
            }
        }
        return null; 
    }

    public String getTokenByUser(User user) {
        return userRepository.findById(user.getIDUsuario()).
        orElseThrow(() -> new RuntimeException("No encontrado")).getToken();
        
    }

    /*ELIMINAR USUARIO */
    public boolean removeUsuarioYCasos(String userEmailDTO) {
        User usuarioAEliminar = null;

        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(userEmailDTO)) {
                usuarioAEliminar = user;
                break;
            }
        }

        if (usuarioAEliminar == null) {
            return false;
        }

        userRepository.deleteById(usuarioAEliminar.getIDUsuario());
        //ELIMINAR SUS CASOS
        // ...

        return true;
    }

    //logout
    public boolean logoutUser(String tokenE){
        User usuarioE= getUserByToken(tokenE);

        if(usuarioE!= null){
            userRepository.findById(usuarioE.getIDUsuario()).
            orElseThrow(() -> new RuntimeException("No encontrado")).setLogin(false);
            userRepository.findById(usuarioE.getIDUsuario()).
            orElseThrow(() -> new RuntimeException("No encontrado")).setToken(null);

            return true;
        }

        return false;
    }


}
