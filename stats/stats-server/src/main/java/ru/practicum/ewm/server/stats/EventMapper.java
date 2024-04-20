package ru.practicum.ewm.server.stats;

import ru.practicum.ewm.dto.stats.EventDto;
import ru.practicum.ewm.dto.stats.EventGetDto;
import ru.practicum.ewm.server.stats.entity.Event;
import ru.practicum.ewm.server.stats.entity.EventForGet;

import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToDate;

public class EventMapper {

    public static Event convertToEntity(EventDto eventDto) {
        return new Event(eventDto.getApp(), eventDto.getUri(), eventDto.getIp(), convertToDate(eventDto.getExistDate()));
    }

    public static EventGetDto convertToGetDto(EventForGet event) {
        return new EventGetDto(event.getApp(), event.getUri(), event.getCount());
    }
}
