package ru.practicum.ewm.server.stats;

import ru.practicum.ewm.dto.stats.StatEventDto;
import ru.practicum.ewm.server.stats.entity.StatEvent;

import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToDate;

public class EventMapper {

    public static StatEvent convertToEntity(StatEventDto eventDto) {
        return new StatEvent(eventDto.getApp(), eventDto.getUri(), eventDto.getIp(), convertToDate(eventDto.getExistDate()));
    }
}
