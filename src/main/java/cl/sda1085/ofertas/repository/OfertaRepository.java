package cl.sda1085.ofertas.repository;

import cl.sda1085.ofertas.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {


}
