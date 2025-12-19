package es.deusto.sd.authenticus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import es.deusto.sd.authenticus.dao.*;
import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.entity.User;
import es.deusto.sd.authenticus.entity.UserToken;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    private final AtomicLong idGenerator = new AtomicLong(0);

    public UserService(UserRepository userRepository, 
        UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
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
        if(tieneMayus==true &&tieneMinus==true && tieneNumero==true && tieneSimbolo==true && tiene8letras==true){
            valido=true;
        }
        return valido;
    }

    public User busquedaEmailPassword(LoginRequestDTO miUser){
        String email=miUser.getEmail();
        String password = miUser.getPassword();
        Optional<User> user = userRepository.findByEmailAndPassword(email,password);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null; 
        }
    }

    private UserDTO convertToDTO(User User) {
        return new UserDTO(User.getIDUsuario(), User.getNombre(), 
        User.getEmail(), User.getPassword(),User.getTel());
    }

    public ArrayList<UserDTO> getAllUsersRegistrados(){
        ArrayList<UserDTO> listUsersDTOs = new ArrayList<UserDTO>();
        
        for(User User1: userRepository.findAll()){
            listUsersDTOs.add(convertToDTO(User1));
        }
        return listUsersDTOs;
    }

    @Transactional
    public UserToken generacionAsignacionToken(User usuarioLogIn){
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        
        User userEncontrado = userRepository.findById(usuarioLogIn.getIDUsuario())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserToken userTokenDB = userTokenRepository.findById(userEncontrado.getIDUsuario())
            .orElse(new UserToken());

        userTokenDB.setUser(userEncontrado); 
        userTokenDB.setToken(token);

        return userTokenRepository.save(userTokenDB);
    }

    public String getTokenByUser(User user) {
        return userTokenRepository.findById(user.getIDUsuario()).
        orElseThrow(() -> new RuntimeException("No encontrado")).getToken();
        
    }

    public boolean removeUsuarioYCasos(String userEmailDTO) {

        Optional<User> usuarioAEliminar = userRepository.findByEmail(userEmailDTO);

        if (!usuarioAEliminar.isPresent()) {
            return false;
        }

        userRepository.deleteById(usuarioAEliminar.get().getIDUsuario());
        //ELIMINAR SUS CASOS
        // ...

        return true;
    }

    @Transactional
    public boolean logoutUser(String tokenE){

        Optional<UserToken> usuarioE= userTokenRepository.findByToken(tokenE);

        if(usuarioE.isPresent()){
            userTokenRepository.deleteByToken(tokenE);

            return true;
        }

        return false;
    }


}
