package es.deusto.sd.authenticus.controller;

import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

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

import es.deusto.sd.authenticus.dto.UserDTO;
import es.deusto.sd.authenticus.service.Estado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")

public class UserController {

    private final Estado estado;
    public UserController(Estado estado){
        this.estado=estado;
    }

    @Operation(
        summary = "Get Todos los usuarios",
        description = "Te da la info de todos lo usuaios"
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    public ResponseEntity <List<UserDTO>> getAllUsers(){
        ArrayList<UserDTO> usuarios =estado.getAllUsers();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }


    @Operation(
        summary = "Crea un nuevo Usuario",
        description = "Crea un unevo usuario con la información dada"
    )
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
        @Parameter(description = "Objeto User a ser creado", required = true)
        @RequestBody UserDTO userDTO) {
        UserDTO newUser = estado.createUser(userDTO);
        

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
