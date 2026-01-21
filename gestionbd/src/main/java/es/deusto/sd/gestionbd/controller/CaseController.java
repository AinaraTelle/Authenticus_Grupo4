package es.deusto.sd.gestionbd.controller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import es.deusto.sd.gestionbd.dto.*;
import es.deusto.sd.gestionbd.service.CaseService;



@RestController
@RequestMapping("/casos")

public class CaseController{
    private final CaseService caseService;
    
    public CaseController(CaseService caseService){
        this.caseService=caseService;
    }

    //* POST: Crear caso */
    @PostMapping("/crear")
    public ResponseEntity<CasoDTO> crearCaso(
        @RequestHeader("Authorization") String token,
        @RequestBody CreateCasoDTO casoDTO) {
            CasoDTO casoCreado = caseService.crearCaso(token, casoDTO);
            if(casoCreado!=null){
                return new ResponseEntity<>(casoCreado, HttpStatus.CREATED);
            }else{
                return null;
            }
    }


    //* GET: Buscar caso por limite*/
    @GetMapping("/mis-casos")
    public ResponseEntity<List<CasoDTO>> obtenerMisCasos(
        @RequestHeader("Authorization") String token,
        @RequestParam(name = "limite",defaultValue = "5") int limite) {
        try {
            List<CasoDTO> casos =caseService.obtenerCasosDeUsuario(token, limite);
            
            return new ResponseEntity<>(casos, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Por ejemplo, token inválido
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //* GET: Buscar caso*/
    @GetMapping("/buscarCaso/{idCaso}")
    public ResponseEntity<CasoDTO> obtenerArchivos(
        @PathVariable("idCaso") Long idCaso) {
        try {
            CasoDTO casoDTO = caseService.obtenerCaso(idCaso);
            return new ResponseEntity<>(casoDTO, HttpStatus.OK);
            
        } catch (Exception e) {
            // Por ejemplo, token inválido
            e.printStackTrace();
            return null;
        }
    }

    //* GET: Buscar caso por fechas*/
    @GetMapping("/mis-casos-fechas")
    public ResponseEntity<List<CasoDTO>> obtenerMisCasos(
        @RequestHeader("Authorization") String token,
        @RequestParam("inicio") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
        @RequestParam("fin") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        try {
            List<CasoDTO> casosPorfecha = caseService.obtenerCasosDeUsuarioEntreFechas(token, fechaInicio, fechaFin);
            return new ResponseEntity<>(casosPorfecha, HttpStatus.OK);
            
        } catch (/*RuntimeException*/ IllegalAccessException e) {
            // Por ejemplo, token inválido
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (/*IllegalAccessException*/ Exception e) {
            //return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    //* PUT: Añadir archivos */
    @PutMapping("/add-files")
    public ResponseEntity<String> addFilesToCase(
            @RequestHeader("Authorization") String token,
            @RequestParam("idCaso") Long idCaso,
            @RequestBody ArrayList<ArchivoDTO> nuevosArchivos) {
        try {
            caseService.addFilesToCase(token, idCaso, nuevosArchivos);
         return new ResponseEntity<>("Archivos añadidos correctamente", HttpStatus.OK);
            // return ResponseEntity.ok(true);

        } catch (IllegalAccessException e) {
         return new ResponseEntity<>("No tienes permiso o el token no es válido", HttpStatus.UNAUTHORIZED);
            // return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        
        } catch (/*IllegalArgumentException*/ Exception e) {
         return new ResponseEntity<>("Caso no encontrado", HttpStatus.NOT_FOUND);
            // return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //* DELETE: Eliminar casos */
    @DeleteMapping("/eliminar/{idCaso}")
    public ResponseEntity<Boolean> eliminarCaso(
        @RequestHeader("Authorization") String token,
        @PathVariable("idCaso") Long idCaso) {
        try{
            boolean exito = caseService.eliminarCaso(token, idCaso);
            return ResponseEntity.ok(exito);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
//         //* GET: Mostrar resultados*/
//     @Operation(
//     summary = "Muestra los resultados de un caso"
//     )
//     @ApiResponse(responseCode = "200", description = "Archivos añadidos correctamente")
//     @GetMapping("/resultados")
//     public ResponseEntity<ResultadosDTO> mostrarResultados(
//         @RequestParam("idUsuario") int idUsuario,
//         @RequestParam("idCaso") int idCaso) {
//         try {
//             ResultadosDTO reul = caseService.mostrarResultados(idUsuario,idCaso);
//             return new ResponseEntity<>(reul, HttpStatus.OK);
//         } catch (RuntimeException e) {
//             // Por ejemplo, token inválido
//             return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//         }
//     }
}