package cl.sda1085.ofertas.webclient;

import org.springframework.stereotype.Component;



@Component
public class SubastaClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient.Builder webClientBuilder,
                         @Value("${usuarios-service.url}") String urlUsuarios) {
        this.webClient = webClientBuilder.baseUrl(urlUsuarios).build();
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
