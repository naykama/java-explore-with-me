package ru.practicum.ewm.server.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.stats.StatEventDto;
import ru.practicum.ewm.dto.stats.StatEventGetDto;
import ru.practicum.ewm.server.stats.EventMapper;
import ru.practicum.ewm.server.stats.StatRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository repository;

    @Override
    public void saveEvent(StatEventDto eventDto) {
        repository.save(EventMapper.convertToEntity(eventDto));
    }

    @Override
    public List<StatEventGetDto> findEvents(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        List<StatEventGetDto> events;
        if (!unique) {
            if (uris == null) {
                events = repository.findWithNoUniqueIp(start, end);
            } else {
                events = repository.findWithNoUniqueIpByUris(start, end, List.of(uris));
            }
        } else {
            if (uris == null) {
                events = repository.findWithUniqueIp(start, end);
            } else {
                events = repository.findWithUniqueIpByUris(start, end, List.of(uris));
            }
        }
        return events.stream()
                .sorted(Comparator.comparing(StatEventGetDto::getHits).reversed())
                .collect(Collectors.toList());
    }
}
