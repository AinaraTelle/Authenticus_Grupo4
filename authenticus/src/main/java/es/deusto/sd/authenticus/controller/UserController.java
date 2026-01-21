package es.deusto.sd.authenticus.controller;

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

import es.deusto.sd.authenticus.dto.*;
import es.deusto.sd.authenticus.external.*;
import es.deusto.sd.authenticus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")

public class UserController { //maneja las peticiones HTTP de los usuarios

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }   

    //* POST: Registra usuario nuevo */
    @Operation(
        summary = "Crea un nuevo usuario",
        description = "Crea un unevo usuario con la información proporcionada"
    )
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody RegisterRequestDTO userRegisterDTO) {

        UserDTO newUser = userService.createUser(userRegisterDTO);
        if(newUser!=null){
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    //* POST: login */
    @Operation(
        summary = "Hace login de un usuario"
    )
    @ApiResponse(responseCode = "201", description = "Usuario loggeado correctamente")
    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> userLogIn(
        @RequestBody LoginRequestDTO userLogin) {

            UserTokenDTO userTokenDTO=userService.login(userLogin);
            if(userTokenDTO!=null){
                return new ResponseEntity<>(userTokenDTO, HttpStatus.OK);
            }else{
                return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE);
            }
        }
    

    // //* DELETE: Usuario */
    @Operation(
        summary = "Elimina un usuario y sus casos de investigacion",
        description = "Elimina completamente la informacion del usuario y todos los casos asociados"
    )
    @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @DeleteMapping("/remove/{email}")
    public ResponseEntity<String> removeUser(@PathVariable("email") String userEmailDTO) {
        boolean eliminado = userService.removeUsuarioYCasos(userEmailDTO);

        if (!eliminado) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Usuario y sus casos eliminados correctamente", HttpStatus.OK);
    }


    // //* POST: logout */
    @Operation(
        summary = "Hace logout de un usuario. A diferencia de la demás funciones, aquí, se debe indicar al token del usuario en el body."
    )
    @ApiResponse(responseCode = "200", description = "Usuario desloggeado correctamente")
    @ApiResponse(responseCode = "404", description = "Usuario no eliminado")
    @PostMapping("/logout")
    public ResponseEntity<String> userLogout(@RequestBody LogoutRequestDTO logoutRequest){
        try {
            String token= logoutRequest.getToken();

            boolean logoutE = userService.logoutUser(token);

            if(logoutE==true){
                return new ResponseEntity<>("Usuario desloggeado correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Token no válido", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>("Token no válido", HttpStatus.NOT_FOUND);
        }
    }

    // //* GET: Usuarios registrados */
    @Operation(
         summary = "Devuelve todos los usuarios registrados",
         description = "Obtiene la información de todos los usuarios desde el almacenamiento externo"
     )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    public ResponseEntity <List<UserDTO>> getAllUsers(){     
        List<UserDTO> usuarios =userService.getAllUsersRegistrados();

        if (usuarios != null) {
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    //  return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }

}
