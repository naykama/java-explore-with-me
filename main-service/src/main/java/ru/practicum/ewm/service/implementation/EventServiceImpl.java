package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.controller.EventController;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.update.EventAdminUpdateDto;
import ru.practicum.ewm.dto.event.update.EventBaseUpdateDto;
import ru.practicum.ewm.dto.event.update.EventUpdateDto;
import ru.practicum.ewm.dto.request.RequestDto;
import ru.practicum.ewm.dto.request.RequestStatusGetDto;
import ru.practicum.ewm.dto.request.RequestStatusUpdateDto;
import ru.practicum.ewm.entity.*;
import ru.practicum.ewm.entity.enums.AdminActionType;
import ru.practicum.ewm.entity.enums.RequestType;
import ru.practicum.ewm.entity.enums.StateType;
import ru.practicum.ewm.entity.enums.UserActionType;
import ru.practicum.ewm.entity.exception.ConflictException;
import ru.practicum.ewm.entity.exception.NotFoundException;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.repository.*;
import ru.practicum.ewm.service.EventService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToDate;
import static ru.practicum.ewm.mapper.EventMapper.convertToDto;
import static ru.practicum.ewm.mapper.EventMapper.convertToEntity;
import static ru.practicum.ewm.utils.CustomPage.getPage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final ViewRepository viewRepository;

    @Override
    public EventDto createEvent(long userId, EventShortDto eventShortDto, LocalDateTime createDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        });
        Category category = categoryRepository.findById(eventShortDto.getCategory().getId()).orElseThrow(() -> {
            log.error("Category with id = {} not found", eventShortDto.getCategory().getId());
            throw new NotFoundException(String.format("Category with id = %d not found", eventShortDto.getCategory().getId()));
        });
        Event event = convertToEntity(eventShortDto, category, createDate, user);
        event.setState(StateType.PENDING);
        event = eventRepository.save(event);
        return convertToDto(event,0, getConfirmedRequestsCount(event.getId()));
    }

    @Override
    public List<EventDto> findEventsForUser(long userId, int from, int size) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        }
        return eventRepository.findByInitiatorId(userId, getPage(from, size)).stream()
                .map((event) -> convertToDto(event, getViews(event.getId()), getConfirmedRequestsCount(event.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findEventForUser(long userId, long eventId, String clientIp) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        }
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> {
            log.error("Event not found");
            throw new NotFoundException(String.format("Event with id =  %d not found", userId));
        }
        );
        EventDto dto = convertToDto(eventRepository.save(event), getViews(eventId), getConfirmedRequestsCount(eventId));
        setViews(eventId, clientIp);
        return dto;
    }

    @Override
    public List<RequestDto> findRequestToEvent(long eventId, long userId) {
        if (!userRepository.existsById(userId) || !eventRepository.existsById(eventId)) {
            log.error("User with id = {} or Event with id = {} not found", userId, eventId);
            throw new NotFoundException(String.format("User with id = %d or event with id = %d not found", userId, eventId));
        }
        return requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId).stream()
                .map(RequestMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto updateEvent(long userId, long eventId, EventUpdateDto newEvent) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> {
            log.error("Event with id = {} and initiatorId = {}  not found", eventId, userId);
            throw new NotFoundException(String.format("Event with id = %d and initiatorId = %d not found",eventId, userId));
            }
        );
        if (event.getState() == StateType.PUBLISHED) {
            log.error("Cannot update published events");
            throw new ConflictException("Cannot update published events");
        }
        Event updatedEvent = updateEventFromDto(newEvent, event);
        if (newEvent.getStateAction() != null) {
            updatedEvent.setState(newEvent.getStateAction() == UserActionType.CANCEL_REVIEW
                                                    ? StateType.CANCELED : StateType.PENDING);
        }
        return convertToDto(eventRepository.save(updatedEvent),getViews(eventId), getConfirmedRequestsCount(eventId));
    }

    @Override
    public List<EventDto> findAllForAdmin(Long[] users, String[] states, Long[] categories, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                   int from, int size) {
        Pageable pageConfig = getPage(from, size);
        return eventRepository.findAllByParams(
                                                        users == null || users[0] == 0 ? null : Arrays.asList(users),
                                                        states == null ? null : Arrays.stream(states)
                                                                                .map(StateType::valueOf)
                                                                                .collect(Collectors.toList()),
                                                        categories == null || categories[0] == 0 ? null : Arrays.asList(categories),
                                                        rangeStart, rangeEnd, pageConfig)
                .stream()
                .map((event) -> convertToDto(event, getViews(event.getId()), getConfirmedRequestsCount(event.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventDto updateEventByAdmin(long eventId, EventAdminUpdateDto newEvent) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
                    log.error("Event with id = {} not found", eventId);
                    throw new NotFoundException(String.format("Event with id = {} not found", eventId));
                }
        );
        checkUpdate(event, newEvent);
        Event updatedEvent = updateEventFromDto(newEvent, event);
        if (newEvent.getStateAction() != null) {
            if (newEvent.getStateAction() == AdminActionType.PUBLISH_EVENT) {
                updatedEvent.setState(StateType.PUBLISHED);
                updatedEvent.setPublishDate(LocalDateTime.now());
            } else {
                updatedEvent.setState(StateType.CANCELED);
            }
        }
        return convertToDto(eventRepository.save(updatedEvent), getViews(eventId), getConfirmedRequestsCount(eventId));
    }

    @Override
    public List<EventDto> findAllEvents(String text, Long[] categories, Boolean isPaid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, boolean isOnlyAvailable, EventController.SortType sort,
                                        int from, int size) {
        Pageable pageConfig = getPage(from, size);
        return sort == null ?
                eventRepository.findAllForPublic(text,
                        categories == null || categories[0] == 0 ? null : List.of(categories), isPaid,
                        rangeStart == null ? LocalDateTime.now() : rangeStart, rangeEnd, isOnlyAvailable,
                        pageConfig).stream()
                    .map((event) -> convertToDto(event, getViews(event.getId()), getConfirmedRequestsCount(event.getId())))
                    .collect(Collectors.toList())
                : sort == EventController.SortType.EVENT_DATE ?
                eventRepository.findAllForPublic(text,
                                categories == null || categories[0] == 0 ? null : List.of(categories), isPaid,
                                rangeStart == null ? LocalDateTime.now() : rangeStart, rangeEnd, isOnlyAvailable,
                                pageConfig).stream()
                        .sorted(Comparator.comparing(Event::getExistDate))
                        .map((event) -> convertToDto(event, getViews(event.getId()), getConfirmedRequestsCount(event.getId())))
                        .collect(Collectors.toList())
                : eventRepository.findAllForPublic(text,
                        categories == null || categories[0] == 0 ? null : List.of(categories), isPaid,
                        rangeStart == null ? LocalDateTime.now() : rangeStart, rangeEnd, isOnlyAvailable,
                        pageConfig).stream()
//                .sorted((event) -> Comparator.comparingLong(getViews(event.getId())))
                .map((event) -> convertToDto(event, getViews(event.getId()), getConfirmedRequestsCount(event.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findEventById(long eventId, String clientIp) {
        Event event = eventRepository.findByIdAndState(eventId, StateType.PUBLISHED).orElseThrow(() -> {
                    log.error("Event with id = {} not found", eventId);
                    return new NotFoundException(String.format("Event with id = %d not found", eventId));
                }
            );
        eventRepository.save(event);
        EventDto dto = convertToDto(event, getViews(eventId), getConfirmedRequestsCount(eventId));
        setViews(eventId, clientIp);
        return dto;
    }

    @Override
    public RequestStatusGetDto updateRequestsStatus(long eventId, long userId, RequestStatusUpdateDto requestUpdateDto) {
        List<Request> requests = requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId);
        long participantCount = requests.stream()
                .filter(request -> request.getStatus() == RequestType.CONFIRMED).count();
        List<Request> updatedRequests = requests.stream()
                .filter(request -> requestUpdateDto.getRequestIds().contains(request.getId()))
                .collect(Collectors.toList());
        if (updatedRequests.size() < requestUpdateDto.getRequestIds().size()) {
            log.error("Not all request are found. Founded requests: {}", updatedRequests);
            throw new NotFoundException(String.format("Not all request are found. Founded requests: %s", updatedRequests));
        }
        long participantLimit = requests.get(0).getEvent().getParticipantLimit();
        if (updatedRequests.stream().anyMatch(request -> request.getStatus() != RequestType.PENDING)) {
            log.error("All requests must have status PENDING");
            throw new ConflictException("All requests must have status PENDING");
        }
        if (participantLimit != 0 && participantCount >= participantLimit) {
            log.error("Limit of participants is already achieved");
            throw new ConflictException("Limit of participants is already achieved");
        }
        switch (requestUpdateDto.getStatus()) {
            case REJECTED:
                for (Request request : updatedRequests) {
                    request.setStatus(RequestType.REJECTED);
                    requestRepository.save(request);
                }
                break;
            case CONFIRMED:
                for (Request request : updatedRequests) {
                    if (participantCount == participantLimit) {
                        request.setStatus(RequestType.REJECTED);
                        requestRepository.save(request);
                    } else {
                        request.setStatus(RequestType.CONFIRMED);
                        participantCount++;
                        requestRepository.save(request);
                    }
                }
                break;
        }
        return new RequestStatusGetDto(
                updatedRequests.stream()
                    .filter(request -> request.getStatus() == RequestType.CONFIRMED)
                    .map(RequestMapper::convertToDto)
                    .collect(Collectors.toList()),
                updatedRequests.stream()
                    .filter(request -> request.getStatus() == RequestType.REJECTED)
                    .map(RequestMapper::convertToDto)
                    .collect(Collectors.toList()));
    }

    private long getConfirmedRequestsCount(long eventId) {
        return  requestRepository.countByEventIdAndStatus(eventId, RequestType.CONFIRMED);
    }

    private long getViews(long eventId) {
        return viewRepository.countByEventId(eventId);
    }

    private void setViews(long eventId, String clientIp) {
        if (!viewRepository.existsByEventIdAndUserIp(eventId, clientIp)) {
            viewRepository.save(new View(eventId, clientIp));
        }
    }

    private void checkUpdate(Event event, EventAdminUpdateDto newEvent) {
        if (newEvent.getStateAction() != null && event.getState() != StateType.PENDING) {
            log.error("Can publish and reject only pending events");
            throw new ConflictException("Can publish and reject only pending events");
        }
        if (event.getExistDate().minusHours(1).isBefore(LocalDateTime.now())) {
            log.error("Can change events which are later then one hour from now");
            throw new ConflictException("Can change events which are later then one hour from now");
        }
    }

    private Event updateEventFromDto(EventBaseUpdateDto dto, Event entity) {
        if (dto == null) {
            return entity;
        }
        if (dto.getAnnotation() != null) {
            entity.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory().getId()).orElseThrow(() -> {
                log.error("Category with id = {} not found", dto.getCategory().getId());
                throw new NotFoundException(String.format("Category with id = %d not found", dto.getCategory().getId()));
            });
            entity.setCategory(category);
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getExistDate() != null) {
            entity.setExistDate(convertToDate(dto.getExistDate()));
        }
        if (dto.getParticipantLimit() != null) {
            entity.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getIsRequestModeration() != null) {
            entity.setRequestModeration(dto.getIsRequestModeration());
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getIsPaid() != null) {
            entity.setIsPaid(dto.getIsPaid());
        }
        return entity;
    }

}
