package ru.practicum.ewm.service;

import ru.practicum.ewm.controller.EventController;
import ru.practicum.ewm.dto.request.RequestDto;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.update.EventAdminUpdateDto;
import ru.practicum.ewm.dto.event.update.EventUpdateDto;
import ru.practicum.ewm.dto.request.RequestStatusGetDto;
import ru.practicum.ewm.dto.request.RequestStatusUpdateDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDto createEvent(long userId, EventShortDto event, LocalDateTime createDate);

    List<EventDto> findEventsForUser(long userId, int from, int size);

    EventDto findEventForUser(long userId, long eventId);

    EventDto updateEvent(long userId, long eventId, EventUpdateDto event);

    List<EventDto> findAllForAdmin(Long[] users, String[] states, Long[] categories, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                   int from, int size);

    EventDto updateEventByAdmin(long eventId, EventAdminUpdateDto updateParams);

    List<EventDto> findAllEvents(String text, Long[] categories, Boolean isPaid, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd, boolean isOnlyAvailable, EventController.SortType sort,
                                 int from, int size);

    EventDto findEventById(long eventId);

    List<RequestDto> findRequestToEvent(long eventId, long userId);

    RequestStatusGetDto updateRequestsStatus(long eventId, long userId, RequestStatusUpdateDto requestUpdateDto);
}
