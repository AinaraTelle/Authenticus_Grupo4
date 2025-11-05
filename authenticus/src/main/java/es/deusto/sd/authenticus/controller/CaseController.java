package es.deusto.sd.authenticus.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
        summary = "Crea un caso al usuario seleccionado mediante el token del mismo que se añada al header"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/mis-casos")
    public ResponseEntity<ArrayList<CasoDTO>> obtenerMisCasos(@RequestHeader("Authorization") String token) {
        try {
            ArrayList<CasoDTO> casos = caseService.obtenerCasosDeUsuario(token);
            return new ResponseEntity<>(casos, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Por ejemplo, token inválido
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}