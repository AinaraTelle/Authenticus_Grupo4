package es.deusto.sd.authenticus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.authenticus.service.Estado;
// import es.deusto.sd.italianrestaurantclass.dto.OrderDTO;
// import es.deusto.sd.italianrestaurantclass.entity.Order.OrderStatus;
// import es.deusto.sd.italianrestaurantclass.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")
public class UsuarioController {
    private final Estado estado;

    public UsuarioController(Estado estado){
        this.estado=estado;
    }
}
