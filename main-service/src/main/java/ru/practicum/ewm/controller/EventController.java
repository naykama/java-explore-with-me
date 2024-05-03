package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.update.EventAdminUpdateDto;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.update.EventUpdateDto;
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

    @GetMapping(ADMIN_PATH)
    public List<EventDto> findAllForAdmin(@RequestParam(required = false) Long[] users,
                                          @RequestParam(required = false) String[] states,
                                          @RequestParam(required = false) Long[] categories,
                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                          @RequestParam(defaultValue = "0") int from,
                                          @RequestParam(defaultValue = "10") int size) {
        checkDates(rangeStart, rangeEnd);
        log.info("Getting events for admin, rangeStart = {}, users = {}, rangeEnd = {}", rangeStart, users, rangeEnd);
        return eventService.findAllForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(ADMIN_PATH + "/{eventId}")
    public EventDto updateEventByAdmin(@PathVariable long eventId, @Valid @RequestBody EventAdminUpdateDto updateParams) {
        log.info("Updating event with id = {} by admin, params = {}", eventId, updateParams);
        return eventService.updateEventByAdmin(eventId, updateParams);
    }

    @GetMapping(PUBLIC_PATH)
    public List<EventDto> findAllEvents(@RequestParam(required = false) String text,
                                        @RequestParam(required = false) Long[] categories,
                                        @RequestParam(name = "paid", required = false) Boolean isPaid,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                        @RequestParam(name = "onlyAvailable", defaultValue = "false") boolean isOnlyAvailable,
                                        @RequestParam(required = false) SortType sort,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        checkDates(rangeStart, rangeEnd);
        log.info("Finding events for public");
        return eventService.findAllEvents(text, categories, isPaid, rangeStart, rangeEnd, isOnlyAvailable, sort, from, size);
    }

    @GetMapping(PUBLIC_PATH + "/{id}")
    public EventDto findEventById(@PathVariable(name = "id") long eventId) {
        log.info("Finding event with id = {} for public", eventId);
        return eventService.findEventById(eventId);
    }

    public enum SortType {
        EVENT_DATE,
        VIEWS
    }

    private void checkDates(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && !rangeEnd.isAfter(rangeStart)) {
            log.error("The dates in range are not correct");
            throw new IllegalArgumentException("The dates in range are not correct");
        }
    }

}
