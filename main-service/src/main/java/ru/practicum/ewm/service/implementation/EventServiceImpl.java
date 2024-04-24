package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.entity.exception.NotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new NotFoundException(String.format("User with id = {} not found", userId));
        });
        Category category = categoryRepository.findById(eventShortDto.getCategory().getId()).orElseThrow(() -> {
            log.error("Category with id = {} not found", eventShortDto.getCategory().getId());
            throw new NotFoundException(String.format("Category with id = {} not found", eventShortDto.getCategory().getId()));
        });
        Event event = eventRepository.save(convertToEntity(eventShortDto, category, createDate, user));
        return convertToDto(event);
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
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = {} not found", userId));
        }
        ));
    }
}
