package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;
import ru.practicum.ewm.dto.compilation.CompilationUpdateDto;
import ru.practicum.ewm.dto.event.EventCompilationDto;
import ru.practicum.ewm.entity.Compilation;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.practicum.ewm.mapper.CompilationMapper.convertToDto;
import static ru.practicum.ewm.mapper.CompilationMapper.convertToEntity;
import static ru.practicum.ewm.utils.CustomPage.getPage;

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
        if (compilation.getEventIds() == null || compilation.getEventIds().size() == 0) {
            return convertToDto(compilationRepository.save(convertToEntity(compilation)));
        }
        List<Event> events = eventRepository.findAllByIdIn(compilation.getEventIds());
        if (events.size() < compilation.getEventIds().size()) {
                log.error("Not all events are exists");
                throw new NotFoundException("Not all events are exists");
            }
        return convertToDto(compilationRepository.save(convertToEntity(compilation, events)), getEventDto(events));
    }

    @Override
    public void deleteCompilationById(long id) {
        try {
            compilationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Category with id = {} not found", id);
            throw new NotFoundException(String.format("Category with id = %d not found", id));
        }
    }

    @Override
    public CompilationDto updateCompilation(long id, CompilationUpdateDto compilationDto) {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> {
            log.error("Compilation with id = {} not found", id);
            return new NotFoundException(String.format("Compilation with id = %d not found", id));
        });
        Compilation updatedCompilation = updateFromDto(compilation, compilationDto);
        if (compilationDto.getEventIds() == null) {
            return convertToDto(updatedCompilation);
        }
        List<Event> events = eventRepository.findAllByIdIn(compilationDto.getEventIds());
        if (events.size() < compilationDto.getEventIds().size()) {
            log.error("Not all events are exists");
            throw new NotFoundException("Not all events are exists");
        }
        updatedCompilation.setEvents(new HashSet<>(events));
        return convertToDto(compilationRepository.save(updatedCompilation), getEventDto(events));
    }

    @Override
    public List<CompilationDto> findAllCompilations(Boolean isPinned, int from, int size) {
        Pageable pageConfig = getPage(from, size);
        return (isPinned == null ? compilationRepository.findAll(pageConfig)
                : compilationRepository.findAllByIsPinned(isPinned, pageConfig)).stream()
                    .map((compilation) -> compilation.getEvents().isEmpty() ? convertToDto(compilation)
                            : convertToDto(compilation, getEventDto(new ArrayList<>(compilation.getEvents()))))
                    .collect(Collectors.toList());
    }

    @Override
    public CompilationDto findCompilationById(long id) {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> {
            log.error("Compilation with id = {} not found", id);
            return new NotFoundException(String.format("Compilation with id = %d not found", id));
        });
        return compilation.getEvents().isEmpty() ? convertToDto(compilation) : convertToDto(compilation,
                getEventDto(new ArrayList<>(compilation.getEvents())));
    }

    private Compilation updateFromDto(Compilation entity, CompilationUpdateDto dto) {
        if (dto == null) {
            return entity;
        }
        if (dto.getIsPinned() != null) {
            entity.setPinned(dto.getIsPinned());
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        return entity;
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
