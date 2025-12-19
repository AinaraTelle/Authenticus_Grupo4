// package es.deusto.sd.authenticus.controller;

// import java.time.LocalDateTime;
// import java.util.ArrayList;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.PathVariable;

// import es.deusto.sd.authenticus.dto.*;
// import es.deusto.sd.authenticus.entity.Archivo;
// import es.deusto.sd.authenticus.service.CaseService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.tags.Tag;



// @RestController
// @RequestMapping("/casos")
// @Tag(name="Casos", description="Operaciones sobre casos")

// public class CaseController{
//     private final CaseService caseService;
    
//     public CaseController(CaseService caseService){
//         this.caseService=caseService;
//     }

//     //* POST: Crear caso */
//     @Operation(
//         summary = "Crea un caso a un usuario. Para ello, se debe hacer los siguiente: en PostMan, acceder a 'Authorization'."+
//         " Ahí, pulsando 'Bearer token', añadir el token del usuario proporcionado en login"
//     )
//     @ApiResponse(responseCode = "201", description = "Caso creado correctamente")
//     @PostMapping("/crear")
//     public ResponseEntity<CasoDTO> crearCaso(
//         @RequestHeader("Authorization") String token,
//         @RequestBody CreateCasoDTO casoDTO) {
//             CasoDTO casoCreado = caseService.crearCaso(token, casoDTO);
//             return new ResponseEntity<>(casoCreado, HttpStatus.CREATED);
//     }


//     //* GET: Buscar caso por limite*/
//     @Operation(
//         summary = "Buscar casos asignados a un usuario. Por defecto se buscarán 5 casos, aunque, este valor se puede cambiar proporcinando este parámetro: en key la palabra 'limit', y, en su valor la cantidad de archivos que queramos observar"
//     )
//     @ApiResponse(responseCode = "200", description = "OK")
//     @GetMapping("/mis-casos")
//     public ResponseEntity<ArrayList<CasoDTO>> obtenerMisCasos(
//         @RequestHeader("Authorization") String token,
//         @RequestParam(defaultValue = "5") int limite) {
//         try {
//             ArrayList<CasoDTO> casos =new ArrayList<>(caseService.obtenerCasosDeUsuario(token).stream().limit(limite).toList());
            
//             return new ResponseEntity<>(casos, HttpStatus.OK);
//         } catch (RuntimeException e) {
//             // Por ejemplo, token inválido
//             return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//         }
//     }


//     //* GET: Buscar caso por fechas*/
//     @Operation(
//         summary = "Busca casos que solo están entre las 2 fechas que pasamos por parámetro. Acceder a documentación para ver cómo indicar las fechas"
//     )
//     @ApiResponse(responseCode = "200", description = "OK")
//     @GetMapping("/mis-casos-fechas")
//     public ResponseEntity<ArrayList<CasoDTO>> obtenerMisCasos(
//         @RequestHeader("Authorization") String token,
//         @RequestParam("inicio") LocalDateTime fechaInicio,
//         @RequestParam("fin") LocalDateTime fechaFin) {
//         try {
//            ArrayList<CasoDTO>casosporfecha=caseService.obtenerCasosDeUsuarioEntreFechas(token, fechaInicio, fechaFin);
//             return new ResponseEntity<>(casosporfecha, HttpStatus.OK);
            
//         } catch (RuntimeException e) {
//             // Por ejemplo, token inválido
//             return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//         }
//     }

    
//     //* PUT: Añadir archivos */
//     @Operation(
//     summary = "Añade archivos adicionales a un caso del usuario autenticado"
//     )
//     @ApiResponse(responseCode = "200", description = "Archivos añadidos correctamente")
//     @ApiResponse(responseCode = "401", description = "Token inválido o no autorizado")
//     @ApiResponse(responseCode = "404", description = "Caso no encontrado")
//     @PutMapping("/add-files")
//     public ResponseEntity<String> addFilesToCase(
//             @RequestHeader("Authorization") String token,
//             @RequestParam("idCaso") int idCaso,
//             @RequestBody ArrayList<Archivo> nuevosArchivos) {
//         try {
//             caseService.addFilesToCase(token, idCaso, nuevosArchivos);
//             return new ResponseEntity<>("Archivos añadidos correctamente", HttpStatus.OK);

//         } catch (IllegalAccessException e) {
//             return new ResponseEntity<>("No tienes permiso o el token no es válido", HttpStatus.UNAUTHORIZED);

//         } catch (IllegalArgumentException e) {
//             return new ResponseEntity<>("Caso no encontrado", HttpStatus.NOT_FOUND);
//         }
//     }

//     //* DELETE: Eliminar casos */
//     @Operation(summary = "Elimina un caso de un usuario. Para ello, se debe proporcionar el token del usario y el id del caso que se quiera eliminar")
//     @ApiResponse(responseCode = "200", description = "Caso eliminado correctamente")
//     @ApiResponse(responseCode = "404", description = "Caso no encontrado")
//     @DeleteMapping("/eliminar/{idCaso}")
//     public ResponseEntity<String> eliminarCaso(@RequestHeader("Authorization") String token,@PathVariable int idCaso) {
//         boolean exito = caseService.eliminarCaso(token, idCaso);
//         if(exito){
//             return new ResponseEntity<>("Caso eliminado correctamente", HttpStatus.OK);
//         } else {
//             return new ResponseEntity<>("Caso no encontrado", HttpStatus.NOT_FOUND);
//         }
//     }

    
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
// }