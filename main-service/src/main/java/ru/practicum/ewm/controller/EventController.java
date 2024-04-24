package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class EventController {
    private final EventService eventService;
    private static final String ADMIN_PATH = "/admin/events";
    private static final String PRIVATE_PATH = "/users/{userId}/events";
    private static final String PUBLIC_PATH = "/events";

    @PostMapping(PRIVATE_PATH)
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventDto createEvent(@PathVariable long userId,
                                @Valid @RequestBody EventShortDto event) {
        log.info("Creating event ={}", event);
        return eventService.createEvent(userId, event, LocalDateTime.now());
    }
}
