package cl.sda1085.ofertas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OfertaResponseDTO {

    //DTO de salida (respuesta)
    //No existen las anotaciones de validación

    private Long id;
    private BigDecimal monto;
    private LocalDateTime fechaHora;
    private Long idUsuario;
    private Long idProducto;
}
