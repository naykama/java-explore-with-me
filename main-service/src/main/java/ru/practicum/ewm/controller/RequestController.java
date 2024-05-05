package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.RequestDto;
import ru.practicum.ewm.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/users/{userId}/requests")
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable long userId, @RequestParam long eventId) {
        log.info("Creating request from user id = {} to event = {}", userId, eventId);
        return requestService.createRequest(userId, eventId, LocalDateTime.now());
    }

    @GetMapping
    public List<RequestDto> findAllRequests(@PathVariable long userId) {
        log.info("Finding all requests for user id = {}", userId);
        return requestService.findAll(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable long userId, @PathVariable long requestId) {
        log.info("Cancel request id = {} from user id = {}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }
}
