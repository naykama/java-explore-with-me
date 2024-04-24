package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDto createEvent(long userId, EventShortDto event, LocalDateTime createDate);

    List<EventDto> findEventsForUser(long userId, int from, int size);

    EventDto findEventForUser(long userId, long eventId);
}
