package cl.sda1085.ofertas.service;

import cl.sda1085.ofertas.dto.OfertaRequestDTO;
import cl.sda1085.ofertas.dto.OfertaResponseDTO;
import cl.sda1085.ofertas.model.Oferta;
import cl.sda1085.ofertas.repository.OfertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    //==============================
    //CRUD estándar
    //==============================

    //Método de apoyo para encapsulamiento de datos
    private OfertaResponseDTO mapToResponseDTO(Oferta oferta) {
        return new OfertaResponseDTO(
                oferta.getId(),
                oferta.getMonto(),
                oferta.getFechaHora(),
                oferta.getIdUsuario(),
                oferta.getIdSubasta()
        );
    }

    //Obtener todas las ofertas
    public List<OfertaResponseDTO> obtenerTodas() {
        return ofertaRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Obtener oferta por ID
    public Optional<OfertaResponseDTO> obtenerPorId(Long id) {
        return ofertaRepository.findById(id).map(this::mapToResponseDTO);
    }

    //Guardar (crear) oferta
    public OfertaResponseDTO guardar(OfertaRequestDTO dto) {
        Oferta oferta = new Oferta();
        oferta.setMonto(dto.getMonto());
        oferta.setIdUsuario(dto.getIdUsuario());
        oferta.setIdSubasta(dto.getIdSubasta());  //IMPORTANTE: La oferta suele ligarse al ID de la SUBASTA
        oferta.setFechaHora(LocalDateTime.now());  //Registro del momento exacto de la puja

        return mapToResponseDTO(ofertaRepository.save(oferta));
    }

    //Actualizar oferta
    public Optional<OfertaResponseDTO> actualizar(Long id, OfertaRequestDTO dto){
        return ofertaRepository.findById(id).map(ofertaExistente -> {
            ofertaExistente.setMonto(dto.getMonto());
            ofertaExistente.setIdUsuario(dto.getIdUsuario());
            ofertaExistente.setIdSubasta(dto.getIdSubasta());

            return mapToResponseDTO(ofertaRepository.save(ofertaExistente));
        });
    }

    //Eliminar oferta
    public void eliminar(Long id){
        ofertaRepository.deleteById(id);
    }


    //==============================
    //CRUD personalizado
    //==============================

    //Obtener la oferta más alta (Posible ganador)
    public Optional<OfertaResponseDTO> obtenerOfertaMasAlta(Long idSubasta) {
        return ofertaRepository.findFirstByIdSubastaOrderByMontoDesc(idSubasta)
                .map(this::mapToResponseDTO);
    }

    //Buscar ofertas de un usuario específico
    public List<OfertaResponseDTO> obtenerOfertasPorUsuario(Long idUsuario) {
        return ofertaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Contar ofertas de una subasta (Devuelve Long, no DTO)
    public Long contarOfertasPorSubasta(Long idSubasta) {
        return ofertaRepository.countByIdSubasta(idSubasta);
    }

    //Buscar ofertas que superen un monto en una subasta
    public List<OfertaResponseDTO> obtenerOfertasMayoresA(Long idSubasta, BigDecimal monto) {
        return ofertaRepository.findByIdSubastaAndMontoGreaterThan(idSubasta, monto).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Verificar si un usuario ya participó (Devuelve boolean)
    public boolean verificarSiUsuarioOferto(Long idUsuario, Long idSubasta) {
        return ofertaRepository.existsByIdUsuarioAndIdSubasta(idUsuario, idSubasta);
    }

    //Obtener el Top 3 de ofertas
    public List<OfertaResponseDTO> obtenerTop3Subasta(Long idSubasta) {
        return ofertaRepository.findTop3ByIdSubastaOrderByMontoDesc(idSubasta).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
}
