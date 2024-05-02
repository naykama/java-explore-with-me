package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.User;

import java.time.LocalDateTime;

import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToDate;
import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToString;

public class EventMapper {
    public static Event convertToEntity(EventShortDto eventDto, Category category, LocalDateTime createDate,
                                        User initiator) {
        return Event.builder().annotation(eventDto.getAnnotation()).category(category)
                .description(eventDto.getDescription())
                .existDate(convertToDate(eventDto.getExistDate()))
                .createDate(createDate).initiator(initiator)
                .locationLat(eventDto.getLocation().getLat())
                .locationLon(eventDto.getLocation().getLon())
                .isPaid(eventDto.isPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .isRequestModeration(eventDto.isRequestModeration())
                .title(eventDto.getTitle()).build();
    }

    public static EventDto convertToDto(Event event) {
        return new EventDto(event.getAnnotation(), event.getId(), CategoryMapper.convertToDto(event.getCategory()),
                convertToString(event.getCreateDate()), event.getDescription(), convertToString(event.getExistDate()),
                UserMapper.convertToWithoutEmailDto(event.getInitiator()),
                new Location(event.getLocationLat(), event.getLocationLon()),
                event.getIsPaid(),
                event.getParticipantLimit(),
                event.isRequestModeration(), event.getTitle(), event.getState(), convertToString(event.getPublishDate()));
    }
}
