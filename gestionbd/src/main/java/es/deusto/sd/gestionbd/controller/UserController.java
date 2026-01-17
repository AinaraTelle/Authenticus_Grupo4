package es.deusto.sd.gestionbd.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.gestionbd.dto.*;
import es.deusto.sd.gestionbd.entity.*;
import es.deusto.sd.gestionbd.service.UserService;

@RestController
@RequestMapping("/usuarios")

public class UserController { //maneja las peticiones HTTP de los usuarios

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }   

    //* POST: Registra usuario nuevo */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody RegisterRequestDTO userDTO) {

        UserDTO newUser = userService.createUser(userDTO);
        if(newUser!=null){
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    //* POST: login */
    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> userLogIn(
        @RequestBody LoginRequestDTO userLogin) {

            User userLoginEncontrado=userService.busquedaEmailPassword(userLogin);
            
            if (userLoginEncontrado==null){
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }else{

            UserToken userToken= userService.generacionAsignacionToken(userLoginEncontrado);

            UserTokenDTO response = 
            new UserTokenDTO(userLoginEncontrado.getEmail(), userToken.getToken());

            return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    

    //* DELETE: Usuario */
    @DeleteMapping("/remove/{email}")
    public ResponseEntity<String> removeUser(@PathVariable("email") String userEmailDTO) {
        boolean eliminado = userService.removeUsuarioYCasos(userEmailDTO);

        if (!eliminado) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Usuario y sus casos eliminados correctamente", HttpStatus.OK);
    }


    //* POST: logout */
    @PostMapping("/logout")
    public ResponseEntity<String> userLogout(@RequestBody LogoutRequestDTO logoutRequest){
        String tokenE= logoutRequest.getToken();

        boolean logoutE = userService.logoutUser(tokenE);

        if(logoutE==true){
            return new ResponseEntity<>("Usuario desloggeado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token no válido", HttpStatus.NOT_FOUND);
        }
    }

    //* GET: Usuarios registrados */
    @GetMapping
    public ResponseEntity <List<UserDTO>> getAllUsers(){
        List<UserDTO> usuarios =userService.getAllUsersRegistrados();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }

}
