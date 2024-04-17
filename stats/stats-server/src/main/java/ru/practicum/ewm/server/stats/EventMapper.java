package ru.practicum.ewm.server.stats;

import ru.practicum.ewm.dto.stats.EventDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Event convertToEntity(EventDto eventDto) {
        LocalDateTime existDate = LocalDateTime.parse(eventDto.getExistDate(), FORMATTER);
        return new Event(eventDto.getApp(), eventDto.getUri(), eventDto.getIp(), existDate);
    }
}
