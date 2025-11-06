package es.deusto.sd.authenticus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.authenticus.dto.LoginRequestDTO;
import es.deusto.sd.authenticus.dto.LoginResponseDTO;
import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.entity.User;
import es.deusto.sd.authenticus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")

public class UserController { //maneja las peticiones HTTP de los usuarios

    // private final Estado estado;
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(
        summary = "Get Todos los usuarios",
        description = "Te da la info de todos lo usuaios"
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    public ResponseEntity <List<UserDTO>> getAllUsers(){
        ArrayList<UserDTO> usuarios =userService.getAllUsersLogOut();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }


    @Operation(
        summary = "Crea un nuevo Usuario",
        description = "Crea un unevo usuario con la información dada"
    )
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

        UserDTO newUser = userService.createUser(userDTO);
        if(newUser!=null){
            System.out.println("\n---------------------------------");
            System.out.println(newUser.toString());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Operation(
        summary = "Hace login de un Usuario"
    )
    @ApiResponse(responseCode = "201", description = "Usuario loggeado correctamente")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> userLogIn(
        @RequestBody LoginRequestDTO userLogin) {
            boolean valido=false;

            valido=userService.verificacionEmailPassword(userLogin);

            if (valido==false){
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }else{

            User userLoginEncontrado=userService.busquedaUsuarioValido(userLogin);
            userService.generacionAsignacionToken(userLoginEncontrado);
            userService.actualizacionListas(userLoginEncontrado);
            String token = userService.getTokenByUser(userLoginEncontrado); 
            LoginResponseDTO response = new LoginResponseDTO(userLoginEncontrado.getEmail(), token);


            return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    

        /*ELIMINAR USUARIO */
        @Operation(
            summary = "Elimina un usuario y sus casos de investigacion",
            description = "Elimina completamente la informacion del usuario y todos los casos asociados"
        )
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente")
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        @DeleteMapping("/remove")
        public ResponseEntity<String> removeUser(@RequestBody UserDTO userDTO) {
            boolean eliminado = userService.removeUsuarioYCasos(userDTO);

            if (!eliminado) {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>("Usuario y sus casos eliminados correctamente", HttpStatus.OK);
        }

               

}
