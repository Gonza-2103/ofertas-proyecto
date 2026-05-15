package cl.sda1085.ofertas.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class SubastaClient {

    private final WebClient webClient;

    public SubastaClient(WebClient.Builder webClientBuilder,
                         @Value("${subastas-service.url}") String urlSubastas) {
        this.webClient = webClientBuilder.baseUrl(urlSubastas).build();
    }

    public Map<String, Object> obtenerUsuarioPorId(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(new RuntimeException("El usuario con ID " + id + " no existe. No puede realizar ofertas.")))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
