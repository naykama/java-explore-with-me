package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.EventUpdateDto;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.StateType;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.entity.exception.ConflictException;
import ru.practicum.ewm.entity.exception.NotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.EventService;

import java.time.LocalDateTime;
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
        return convertToDto(eventRepository.save(event));
    }

    @Override
    public List<EventDto> findEventsForUser(long userId, int from, int size) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        }
        return eventRepository.findByInitiatorId(userId, getPage(from, size)).stream()
                .map(EventMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findEventForUser(long userId, long eventId) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        }
        return convertToDto(eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> {
            log.error("Event not found");
            throw new NotFoundException(String.format("Event with id =  %d not found", userId));
        }
        ));
    }

    @Override
    public EventDto updateEvent(long userId, long eventId, EventUpdateDto newEvent) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id =  %d not found", userId));
                }
        );
        if (event.getState() == StateType.PUBLISHED) {
            log.error("Cannot update published events");
            throw new ConflictException("Cannot update published events");
        }
        EventDto dto = convertToDto(eventRepository.save(updateEventFromDto(newEvent, event)));
        return dto;
    }

    private Event updateEventFromDto(EventUpdateDto dto, Event entity) {
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
        entity.setState(StateType.CANCELED);
        log.info("Entity: {}", entity);
        return entity;
    }
}
