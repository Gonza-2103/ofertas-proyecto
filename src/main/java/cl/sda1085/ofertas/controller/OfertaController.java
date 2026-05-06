package cl.sda1085.ofertas.controller;


import cl.sda1085.ofertas.dto.OfertaRequestDTO;
import cl.sda1085.ofertas.dto.OfertaResponseDTO;
import cl.sda1085.ofertas.service.OfertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor

public class OfertaController {

    private final OfertaService ofertaService;

    @GetMapping
    public ResponseEntity <List<OfertaResponseDTO>> obtener() {
        return ResponseEntity.ok(ofertaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity <OfertaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ofertaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OfertaResponseDTO> crearOferta(
           @Valid @RequestBody OfertaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ofertaService.guardar(dto));


    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OfertaRequestDTO dto) {

        return ofertaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  eliminar(@PathVariable Long id) {
        ofertaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
