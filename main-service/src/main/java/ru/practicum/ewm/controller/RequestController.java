package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.RequestDto;
import ru.practicum.ewm.service.RequestService;

import java.time.LocalDateTime;

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
}
