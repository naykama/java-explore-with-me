package ru.practicum.ewm.server.stats;

import ru.practicum.ewm.dto.stats.EventDto;

public interface StatService {
    void saveEvent(EventDto eventDto);
}
