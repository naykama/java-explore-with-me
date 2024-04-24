package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;

import java.time.LocalDateTime;

public interface EventService {
    EventDto createEvent(long userId, EventShortDto event, LocalDateTime createDate);
}
