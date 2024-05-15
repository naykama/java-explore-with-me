package ru.practicum.ewm.server.stats.service;

import ru.practicum.ewm.dto.stats.StatEventDto;
import ru.practicum.ewm.dto.stats.StatEventGetDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    Long saveEvent(StatEventDto eventDto);

    List<StatEventGetDto> findEvents(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
