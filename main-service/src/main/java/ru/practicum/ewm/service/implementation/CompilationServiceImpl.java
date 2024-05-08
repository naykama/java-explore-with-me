package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;
import ru.practicum.ewm.dto.event.EventCompilationDto;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.View;
import ru.practicum.ewm.entity.enums.RequestType;
import ru.practicum.ewm.entity.exception.NotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.repository.ViewRepository;
import ru.practicum.ewm.service.CompilationService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.practicum.ewm.mapper.CompilationMapper.convertToDto;
import static ru.practicum.ewm.mapper.CompilationMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final ViewRepository viewRepository;
    private final RequestRepository requestRepository;

    @Override
    public CompilationDto createCompilation(CompilationShortDto compilation) {
        List<Event> events = compilation.getEventIds() != null ? eventRepository.findAllByIdIn(compilation.getEventIds()) : null;
        if (events != null && events.size() < compilation.getEventIds().size()) {
                log.error("Not all events are exists");
                throw new NotFoundException("Not all events are exists");
            }
        if (compilation.getEventIds() == null) {
            return convertToDto(compilationRepository.save(convertToEntity(compilation)));
        }
        return convertToDto(compilationRepository.save(convertToEntity(compilation, events)), getEventDto(events));
    }

    private List<EventCompilationDto> getEventDto(List<Event> events) {
        List<Long> eventIds = events.stream()
            .map(Event::getId)
            .collect(Collectors.toList());
        Map<Long, Long> viewCountByEventId = viewRepository.findAllByEventIdIn(eventIds)
                .stream()
                .map(View::getEventId)
                .collect(Collectors.groupingBy(
                    Function.identity(),
                    Collectors.collectingAndThen(Collectors.counting(), Long::longValue)));
        Map<Long, Long> countRequestsById = requestRepository.findAllByEventIdInAndStatus(eventIds, RequestType.CONFIRMED).stream()
                .map(request -> request.getEvent().getId())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::longValue)));
        return events.stream()
                .map(event -> EventMapper.convertToCompilationDto(event,
                        viewCountByEventId.getOrDefault(event.getId(), 0L),
                        countRequestsById.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());
    }
}
