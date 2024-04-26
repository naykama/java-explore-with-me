package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.EventUpdateDto;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping(PRIVATE_PATH)
    public List<EventDto> findEventsForUser(@PathVariable long userId,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting all events for user ={}", userId);
        return eventService.findEventsForUser(userId, from, size);
    }

    @GetMapping(PRIVATE_PATH + "/{eventId}")
    public EventDto findEventForUser(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Getting event for user ={}", userId);
        return eventService.findEventForUser(userId, eventId);
    }

    @PatchMapping(PRIVATE_PATH + "/{eventId}")
public EventDto updateEvent(@PathVariable long userId,
                            @PathVariable long eventId,
                            @Valid @RequestBody EventUpdateDto event) {
        log.info("Updating event for user ={}", userId);
        return eventService.updateEvent(userId, eventId, event);
    }

}
