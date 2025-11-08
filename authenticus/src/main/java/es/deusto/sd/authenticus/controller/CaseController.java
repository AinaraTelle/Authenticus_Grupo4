package es.deusto.sd.authenticus.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.authenticus.dto.CasoDTO;
import es.deusto.sd.authenticus.service.CaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/casos")
@Tag(name="Casos", description="Operaciones sobre casos")

public class CaseController{
    private final CaseService caseService;
    
    public CaseController(CaseService caseService){
        this.caseService=caseService;
    }
    @Operation(
        summary = "Crea un caso al usuario seleccionado mediante el token del mismo que se añada al header"
    )
    @ApiResponse(responseCode = "201", description = "Caso creado correctamente")
    @PostMapping("/crear")
    public ResponseEntity<CasoDTO> crearCaso(
        @RequestHeader("Authorization") String token,
        @RequestBody CasoDTO casoDTO) {
            CasoDTO casoCreado = caseService.crearCaso(token, casoDTO);
            return new ResponseEntity<>(casoCreado, HttpStatus.CREATED);
    }
    @Operation(
        summary = "Función que se encarga de buscar casos creados por el usuario, por default esta en 5 aunque en Postman se puede pasar por parametro la cantidad que quiera el usuario"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/mis-casos")
    public ResponseEntity<ArrayList<CasoDTO>> obtenerMisCasos(@RequestHeader("Authorization") String token,
                                                            @RequestParam(defaultValue = "5") int limite) {
        try {
            ArrayList<CasoDTO> casos =new ArrayList<>(caseService.obtenerCasosDeUsuario(token).stream().limit(limite).toList());
            
            return new ResponseEntity<>(casos, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Por ejemplo, token inválido
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @Operation(
        summary = "Busca casos pasando por parametro dos fechas entre los que estaran los casos que salgan en el output"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/mis-casos-fechas")
    public ResponseEntity<ArrayList<CasoDTO>> obtenerMisCasos(@RequestHeader("Authorization") String token,
                                                            @RequestParam("inicio") LocalDateTime fechaInicio,
                                                            @RequestParam("fin") LocalDateTime fechaFin) {
        try {
           ArrayList<CasoDTO>casosporfecha=caseService.obtenerCasosDeUsuarioEntreFechas(token, fechaInicio, fechaFin);
            
            return new ResponseEntity<>(casosporfecha, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Por ejemplo, token inválido
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /* ANADIR ARCHIVOS */
    @Operation(
    summary = "Añadir archivos adicionales a un caso del usuario autenticado"
    )
    @ApiResponse(responseCode = "200", description = "Archivos añadidos correctamente")
    @ApiResponse(responseCode = "401", description = "Token inválido o no autorizado")
    @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    @PostMapping("/add-files")
    public ResponseEntity<String> addFilesToCase(
            @RequestHeader("Authorization") String token,
            @RequestParam("idCaso") int idCaso,
            @RequestBody ArrayList<String> nuevosArchivos) {

        try {
            caseService.addFilesToCase(token, idCaso, nuevosArchivos);
            return new ResponseEntity<>("Archivos añadidos correctamente", HttpStatus.OK);

        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("No tienes permiso o el token no es válido", HttpStatus.UNAUTHORIZED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Caso no encontrado", HttpStatus.NOT_FOUND);
        }
    }


}