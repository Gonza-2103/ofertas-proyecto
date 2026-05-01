package cl.sda1085.ofertas.service;

import cl.sda1085.ofertas.dto.OfertaRequestDTO;
import cl.sda1085.ofertas.dto.OfertaResponseDTO;
import cl.sda1085.ofertas.model.Oferta;
import cl.sda1085.ofertas.repository.OfertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    //Obtener todos con mapeo a DTO
    public List<OfertaResponseDTO> obtenerTodos(){
        return ofertaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Optional<OfertaResponseDTO> obtenerPorId(Long id){
        return ofertaRepository.findById(id)
                .map(this::convertirADTO);  //Transforma la entidad encontrada encontrada a DTO
    }

    public OfertaResponseDTO guardar(OfertaRequestDTO dto){
        Oferta oferta = new Oferta();
        oferta.setMonto(dto.getMonto());
        oferta.setIdUsuario(dto.getIdUsuario());
        oferta.setIdProducto(dto.getIdProducto());

        //Guardar en la base de datos
        Oferta ofertaGuardada = ofertaRepository.save(oferta);

        //Devolver la respuesta como DTO
        return convertirADTO(ofertaGuardada);
    }

    //Método auxiliar de conversión(reutilizable)
    private OfertaResponseDTO convertirADTO(Oferta oferta){
        return new OfertaResponseDTO(
                oferta.getId(),
                oferta.getMonto(),
                oferta.getFechaHora(),
                oferta.getIdUsuario(),
                oferta.getIdProducto()
        );
    }

    public Optional<OfertaResponseDTO> actualizar(Long id, OfertaRequestDTO dto){
        return ofertaRepository.findById(id).map(ofertaExistente -> {
            ofertaExistente.setMonto(dto.getMonto());
            ofertaExistente.setIdUsuario(dto.getIdUsuario());
            ofertaExistente.setIdProducto(dto.getIdProducto());

            return mapToResponseDTO(ofertaRepository.save(ofertaExistente));
        });
    }

    //Método de apoyo para evitar repetición del código
    private OfertaResponseDTO mapToResponseDTO(Oferta oferta) {
        return new OfertaResponseDTO(
                oferta.getId(),
                oferta.getMonto(),
                oferta.getFechaHora(),
                oferta.getIdUsuario(),
                oferta.getIdProducto()
        );
    }

    public void eliminar(Long id){
        ofertaRepository.deleteById(id);
    }
}
