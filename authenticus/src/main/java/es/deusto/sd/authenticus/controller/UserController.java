package es.deusto.sd.authenticus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.deusto.sd.authenticus.service.*;
import es.deusto.sd.authenticus.dto.*;
import es.deusto.sd.authenticus.entity.User;
import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")

public class UserController { //maneja las peticiones HTTP de los usuarios

    private final Estado estado;
    private final UserService userService;

    public UserController(Estado estado,UserService userService){
        this.estado=estado;
        this.userService = userService;
    }

    @Operation(
        summary = "Get Todos los usuarios",
        description = "Te da la info de todos lo usuaios"
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    public ResponseEntity <List<UserDTO>> getAllUsers(){
        ArrayList<UserDTO> usuarios =estado.getAllUsersLogOut();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }


    @Operation(
        summary = "Crea un nuevo Usuario",
        description = "Crea un unevo usuario con la información dada"
    )
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

        UserDTO newUser = estado.createUser(userDTO);
        System.out.println("\n---------------------------------");
        System.out.println(newUser.toString());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Hace login de un Usuario"
    )
    @ApiResponse(responseCode = "201", description = "Usuario loggeado correctamente")
    @PostMapping("/login")
    public ResponseEntity<LoginRequestDTO> userLogIn(
        @RequestBody LoginRequestDTO userLogin) {
            boolean valido=false;

            valido=userService.verificacionEmailPassword(userLogin);

            if (valido==false){
                return new ResponseEntity<>(userLogin, HttpStatus.NOT_FOUND);
            }else{

            User userLoginEncontrado=userService.busquedaUsuarioValido(userLogin);
            userService.generacionAsignacionToken(userLoginEncontrado);
            userService.actualizacionListas(userLoginEncontrado);


            return new ResponseEntity<>(userLogin, HttpStatus.OK);
            }
        }
       

}
