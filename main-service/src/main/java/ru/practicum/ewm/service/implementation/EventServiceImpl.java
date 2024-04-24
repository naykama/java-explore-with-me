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
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.EventService;

import java.time.LocalDateTime;

import static ru.practicum.ewm.mapper.EventMapper.convertToDto;
import static ru.practicum.ewm.mapper.EventMapper.convertToEntity;

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
}
