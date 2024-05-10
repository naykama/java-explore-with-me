package ru.practicum.ewm.server.stats;

import ru.practicum.ewm.dto.stats.StatEventDto;
import ru.practicum.ewm.server.stats.entity.StatEvent;

public class EventStatMapper {

    public static StatEvent convertToEntity(StatEventDto eventDto) {
        return new StatEvent(eventDto.getApp(), eventDto.getUri(), eventDto.getIp(), eventDto.getExistDate());
    }
}
