package ru.practicum.ewm.server.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.stats.EventDto;
import ru.practicum.ewm.dto.stats.EventGetDto;
import ru.practicum.ewm.server.stats.EventMapper;
import ru.practicum.ewm.server.stats.StatRepository;

import java.util.List;

import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToDate;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository repository;

    @Override
    public void saveEvent(EventDto eventDto) {
        repository.save(EventMapper.convertToEntity(eventDto));
    }

    @Override
    public List<EventGetDto> findEvents(String start, String end, String[] uris, boolean unique) {
        List<EventGetDto> events;
        if (!unique) {
            if (uris == null) {
                events = repository.findWithNoUniqueIp(convertToDate(start), convertToDate(end));
            } else {
                events = repository.findWithNoUniqueIpByUris(convertToDate(start), convertToDate(end), List.of(uris));
            }
        } else {
            if (uris == null) {
                events = repository.findWithUniqueIp(convertToDate(start), convertToDate(end));
            } else {
                events = repository.findWithUniqueIpByUris(convertToDate(start), convertToDate(end), List.of(uris));
            }
        }
        return events;
    }
}
