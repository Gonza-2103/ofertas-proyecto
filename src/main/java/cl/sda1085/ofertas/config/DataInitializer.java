package cl.sda1085.ofertas.config;

import cl.sda1085.ofertas.model.Oferta;
import cl.sda1085.ofertas.repository.OfertaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor

public class DataInitializer implements CommandLineRunner {

    private final OfertaRepository ofertaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (ofertaRepository.count() > 0) {
            log.info("Base de datos de ofertas ya contiene datos. Omitiendo inicialización.");
            return;
        }

        log.info("Inicializando la creación de ofertas (pujas) de prueba");


        //Ofertas para el producto 1:Vasija precolombina (precio base: 150.000)

        Oferta oferta1 = new Oferta(
                null,
                new BigDecimal("160000"),
                LocalDateTime.now().minusHours(5),
                5L,
                1L
        );

        Oferta oferta2 = new Oferta(
                null,
                new BigDecimal("175000"),
                LocalDateTime.now().minusHours(2),
                6L,
                1L
        );

        //Ofertas para el producto 2: Espada medieval (precio base: 300.000)
        Oferta oferta3 = new Oferta(
                null,
                new BigDecimal("310000"),
                LocalDateTime.now().minusDays(1),
                7L,
                2L
        );

        //Ofertas para el producto 3: Pintura colonial (precio base: 500.000)
        Oferta oferta4 = new Oferta(
                null,
                new BigDecimal("550000"),
                LocalDateTime.now().minusMinutes(30),
                5L,
                3L
        );

        // Ofertas para el producto 4: Máscara tribal africana (precio base: 220.000)
        Oferta oferta6 = new Oferta(
                null,
                new BigDecimal("230000"),
                LocalDateTime.now().minusDays(2),
                8L,
                4L
        );

        Oferta oferta7 = new Oferta(
                null,
                new BigDecimal("250000"),
                LocalDateTime.now().minusHours(1),
                10L,
                4L
        );

        // Ofertas para el producto 5: Reloj de bolsillo antiguo (precio base: 400.000)
        Oferta oferta8 = new Oferta(
                null,
                new BigDecimal("410000"),
                LocalDateTime.now().minusDays(2),
                7L,
                5L);

        Oferta oferta9 = new Oferta(
                null,
                new BigDecimal("430000"),
                LocalDateTime.now().minusDays(1),
                9L,
                5L);

        Oferta oferta10 = new Oferta(
                null,
                new BigDecimal("460000"),
                LocalDateTime.now().minusHours(5),
                12L,
                5L);

        // Ofertas para el producto 9: Jarron chino antiguo (precio base: 600.000)
        Oferta oferta11 = new Oferta(
                null,
                new BigDecimal("650000"),
                LocalDateTime.now().minusHours(12),
                14L,
                9L);

        // Ofertas para el producto 11: Tapiz medieval (precio base: 520.000)
        Oferta oferta12 = new Oferta(
                null,
                new BigDecimal("530000"),
                LocalDateTime.now().minusDays(4),
                8L,
                11L);

        Oferta oferta13 = new Oferta(
                null,
                new BigDecimal("560000"),
                LocalDateTime.now().minusDays(2),
                5L,
                11L);

        // Ofertas para el producto 14: Armadura Samurai (precio base: 800.000)
        Oferta oferta14 = new Oferta(
                null,
                new BigDecimal("850000.00"),
                LocalDateTime.now().minusDays(3),
                11L,
                14L
        );

        Oferta oferta15 = new Oferta(
                null,
                new BigDecimal("950000.00"),
                LocalDateTime.now().minusDays(1),
                5L,
                14L
        );

        Oferta oferta16 = new Oferta(
                null,
                new BigDecimal("1000000.00"),
                LocalDateTime.now().minusMinutes(15),
                8L,
                14L
        );


        ofertaRepository.saveAll(List.of(
                oferta1, oferta2, oferta3, oferta4, oferta6, oferta7, oferta8,
                oferta9, oferta10, oferta11, oferta12, oferta13, oferta14, oferta15, oferta16));

        log.info("Se han creado 16 ofertas de prueba enlazadas lógicamente a clientes y productos.");
    }
}
