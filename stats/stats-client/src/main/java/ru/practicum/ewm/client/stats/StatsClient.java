package ru.practicum.ewm.client.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.dto.stats.StatEventDto;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StatsClient {
    @Value("${stat_server.url}")
    private String statsServerAddress;
    private final RestTemplate rest;

    public ResponseEntity<Object> post(StatEventDto body) {
        String path = statsServerAddress + "/hit";
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, defaultHeaders());
        ResponseEntity<Object> serverResponse;
        try {
            serverResponse = rest.exchange(path, HttpMethod.POST, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(serverResponse);
    }

    public ResponseEntity<Object> get(String path, Map<String, Object> parameters) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, defaultHeaders());
        ResponseEntity<Object> serverResponse;
        try {
            serverResponse = rest.exchange(path, HttpMethod.GET, requestEntity, Object.class, parameters);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(serverResponse);
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
