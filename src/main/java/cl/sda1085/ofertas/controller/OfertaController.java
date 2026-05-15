package cl.sda1085.ofertas.controller;


import cl.sda1085.ofertas.dto.OfertaRequestDTO;
import cl.sda1085.ofertas.dto.OfertaResponseDTO;
import cl.sda1085.ofertas.service.OfertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor

public class OfertaController {

    private final OfertaService ofertaService;

    //==============================
    //CRUD estándar
    //==============================

    @GetMapping
    public ResponseEntity<List<OfertaResponseDTO>> obtenerTodas() {
        log.info("Recibida petiión gET para listar todas las ofertas");
        return ResponseEntity.ok(ofertaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity <OfertaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ofertaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OfertaResponseDTO> crearOferta(
            @Valid @RequestBody OfertaRequestDTO dto){
        log.warn("Petición POST recibida para crear oferta de usuario: {}", dto.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(ofertaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OfertaRequestDTO dto){
        log.info("Recibida petición PUT para actualizar la oferta ID: {}", id);

        return ofertaService.actualizar(id, dto)
                .map(response -> {
                    log.debug("Respuesta exitosa para actualización de ID: {}", id);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("Respuesta 404: Oferta ID {} no encontrada para actualizar", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  eliminar(@PathVariable Long id) {
        log.warn("Petición de eliminación para la oferta ID: {}", id);
        ofertaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    //==============================
    //CRUD personalizado
    //==============================

    //Busca todas las ofertas realizadas por un usuario específico
    //GET /api/ofertas/usuario/{idUsuario}
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<OfertaResponseDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ofertaService.obtenerOfertasPorUsuario(idUsuario));
    }

    //Cuenta cuántas ofertas se han realizado en una subasta
    //GET /api/ofertas/subasta/{idSubasta}/total
    @GetMapping("/subasta/{idSubasta}/total")
    public ResponseEntity<Long> contarOfertas(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(ofertaService.contarOfertasPorSubasta(idSubasta));
    }

    //Busca ofertas que superen un monto en una subasta específica.
    //GET /api/ofertas/subasta/{idSubasta}/mayores-a?monto=1000
    @GetMapping("/subasta/{idSubasta}/mayores-a")
    public ResponseEntity<List<OfertaResponseDTO>> obtenerOfertasMayores(
            @PathVariable Long idSubasta,
            @RequestParam BigDecimal monto) {
        return ResponseEntity.ok(ofertaService.obtenerOfertasMayoresA(idSubasta, monto));
    }

    //Verifica si un usuario ya ha realizado alguna oferta en una subasta
    //GET /api/ofertas/verificar/usuario/{idUsuario}/subasta/{idSubasta}
    @GetMapping("/verificar/usuario/{idUsuario}/subasta/{idSubasta}")
    public ResponseEntity<Boolean> verificarParticipacion(
            @PathVariable Long idUsuario,
            @PathVariable Long idSubasta) {
        return ResponseEntity.ok(ofertaService.verificarSiUsuarioOferto(idUsuario, idSubasta));
    }

    //Obtiene las 3 ofertas más altas de una subasta (Podium).
    //GET /api/ofertas/subasta/{idSubasta}/top3
    @GetMapping("/subasta/{idSubasta}/top3")
    public ResponseEntity<List<OfertaResponseDTO>> obtenerTop3(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(ofertaService.obtenerTop3Subasta(idSubasta));
    }
}
