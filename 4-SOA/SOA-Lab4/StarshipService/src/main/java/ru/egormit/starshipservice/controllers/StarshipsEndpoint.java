package ru.egormit.starshipservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.egormit.library.StarShipDto;
import ru.egormit.library.StarShipRequest;
import ru.egormit.starshipservice.common.Endpoints;
import ru.egormit.starshipservice.service.StarshipService;

import java.util.List;

@Endpoint
@RequiredArgsConstructor
public class StarshipsEndpoint {

    private final String NAMESPACE_URI = "http://localhost:8081";

    private final StarshipService starshipService;

    @PayloadRoot(namespace = NAMESPACE_URI + Endpoints.CREATE_STARSHIP, localPart = "StarShipRequest")
    @ResponsePayload
    public void createStarship(@RequestPayload StarShipRequest request) {
        starshipService.createStarship(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI + Endpoints.GET_ALL_STARSHIPS, localPart = "StarShipResponse")
    @ResponsePayload
    public List<StarShipDto> getAllStarships() {
        return starshipService.getAllStarships();
    }


}
