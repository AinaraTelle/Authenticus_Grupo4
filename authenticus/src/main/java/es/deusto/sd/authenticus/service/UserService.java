package es.deusto.sd.authenticus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import es.deusto.sd.authenticus.dao.*;
import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.RegisterRequestDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.entity.User;
import es.deusto.sd.authenticus.entity.UserToken;
import es.deusto.sd.authenticus.socket.ClienteSocket;
import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    private final AtomicLong idGenerator = new AtomicLong(0);
    private ClienteSocket socketCliente; //Atributo del socket

    public UserService(UserRepository userRepository, 
        UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
    }
    @PostConstruct
    public void init() throws Exception { // aqui se inicializa
        ClienteSocket socketCliente=null;
        while (socketCliente==null){
            try {
                socketCliente = new ClienteSocket("localhost", 5000);
                break;
           } catch (IOException e) {
               System.err.println("No se pudo conectar al servidor de sockets: " + e.getMessage());
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException ignored) {
               }
           }

        }
        this.socketCliente=socketCliente;
    }
    
    public UserDTO createUser(RegisterRequestDTO createUserDTO) {    
        boolean passwordValida=validacionPassword(createUserDTO.getPassword());

        if(passwordValida==true){

            User user = new User(idGenerator.incrementAndGet(),
            createUserDTO.getNombre(),createUserDTO.getEmail(), 
            createUserDTO.getPassword(), createUserDTO.getTel());

           if (userRepository.existsByEmailIgnoreCase(createUserDTO.getEmail())) {
                return null; // O lanza una excepción personalizada
            }

            user.setIDUsuario(null);
            userRepository.save(user);
            if(socketCliente!=null){
                socketCliente.sendMessage("REGISTER#" + user.getIDUsuario() + "#" + user.getNombre() + "#" + user.getEmail() + "#" + user.getPassword() + "#" + user.getTel() );
            }
            return convertToDTO(user);
            
        }else{
            if(socketCliente!=null){
                socketCliente.sendMessage("REGISTER#"  );
            }
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
            if(socketCliente!=null){
                socketCliente.sendMessage("LOGIN#"+ "OK#" + user.get().getNombre()+ "#" +user.get().getPassword());
            }
            return user.get();
        } else {
            if(socketCliente!=null){
                socketCliente.sendMessage("LOGIN#NO#ERROR#ERROR");
            }
            return null; 
        }
    }

    private UserDTO convertToDTO(User User) {
        return new UserDTO(User.getIDUsuario(), User.getNombre(), 
        User.getEmail(), User.getPassword(),User.getTel());
    }

    public List<UserDTO> getAllUsersRegistrados(){
        List<User> listUsers = userRepository.findAll();
        
        List<UserDTO> listUsersDTO = new ArrayList<UserDTO>() ;

        for(User User1: listUsers){
            listUsersDTO.add(convertToDTO(User1));
        }
        return listUsersDTO;
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
    
    @Transactional
    public boolean removeUsuarioYCasos(String userEmailDTO) {

        Optional<User> usuarioAEliminar = userRepository.findByEmail(userEmailDTO);

        if (!usuarioAEliminar.isPresent()) {
            return false;
        }
        Long id = usuarioAEliminar.get().getIDUsuario();
       // 1. Borramos el token primero para liberar la restricción
        userTokenRepository.deleteById(id); 
        
        // 2. Ahora que el usuario no tiene "hijos" que lo aten, lo podemos borrar
        userRepository.deleteById(id);
        //ELIMINAR SUS CASOS
        // ...
        if(socketCliente!=null){
            socketCliente.sendMessage("REMOVE");
        }
        return true;
    }

    @Transactional
    public boolean logoutUser(String tokenE){

        Optional<UserToken> usuarioE= userTokenRepository.findByToken(tokenE);

        if(usuarioE.isPresent()){
            userTokenRepository.deleteByToken(tokenE);
            if(socketCliente!=null){
                socketCliente.sendMessage("LOGOUT#" + usuarioE.get().getUser().getNombre() );
            }
            if(socketCliente!=null){
                socketCliente.sendMessage("LOGOUT");
            }
            return true;
        }

        return false;
    }


}
