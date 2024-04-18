package ru.practicum.ewm.server.stats.service;

import ru.practicum.ewm.dto.stats.EventDto;
import ru.practicum.ewm.dto.stats.EventGetDto;

import java.util.List;

public interface StatService {
    void saveEvent(EventDto eventDto);

    List<EventGetDto> findEvents(String start, String end, String[] uris, boolean unique);
}
