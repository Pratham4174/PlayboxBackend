package com.playbox.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.playbox.booking.dto.VenueDTO;

@Service
public class VenueIntegrationService {

    private final WebClient webClient;

    public VenueIntegrationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<VenueDTO> getVenuesByOwnerId(Long ownerId) {
        return webClient.get()
                .uri("/venues/owner/{ownerId}", ownerId)
                .retrieve()
                .bodyToFlux(VenueDTO.class)
                .collectList()
                .block(); // You can use `subscribe()` or `block()` depending on need
    }
}
