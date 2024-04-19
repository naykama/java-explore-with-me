package ru.practicum.ewm.server.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.dto.stats.EventDto;
import ru.practicum.ewm.server.stats.entity.Event;
import ru.practicum.ewm.server.stats.service.StatService;
import ru.practicum.ewm.server.stats.service.StatServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToDate;
import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToString;

@ExtendWith(MockitoExtension.class)
public class StatServiceTest {
    private StatService service;
    @Mock
    StatRepository repository;

    @BeforeEach
    public void setUp() {
        service = new StatServiceImpl(repository);
    }

    @Test
    public void saveEventTest() {
        EventDto event = new EventDto("ewm-main-service", "/events/1", "192.163.0.1", "2022-09-06 11:00:23");
        when(repository.save(EventMapper.convertToEntity(event))).thenReturn(EventMapper.convertToEntity(event));
        service.saveEvent(event);
        verify(repository).save(any(Event.class));
    }

    @Test
    public void findEventTest() {
        String start = convertToString(LocalDateTime.now());
        String end = convertToString(LocalDateTime.now().plusDays(5));
        String[] uris = new String[] {"uri1", "uri2"};
        when(repository.findWithNoUniqueIp(convertToDate(start), convertToDate(end))).thenReturn(new ArrayList<>());
        when(repository.findWithNoUniqueIpByUris(convertToDate(start), convertToDate(end), List.of(uris))).thenReturn(new ArrayList<>());
        when(repository.findWithUniqueIp(convertToDate(start), convertToDate(end))).thenReturn(new ArrayList<>());
        when(repository.findWithUniqueIpByUris(convertToDate(start), convertToDate(end), List.of(uris))).thenReturn(new ArrayList<>());

        service.findEvents(start, end, null, false);
        verify(repository).findWithNoUniqueIp(convertToDate(start), convertToDate(end));

        service.findEvents(start, end,uris, false);
        verify(repository).findWithNoUniqueIpByUris(convertToDate(start), convertToDate(end), List.of(uris));

        service.findEvents(start, end, null, true);
        verify(repository).findWithUniqueIp(convertToDate(start), convertToDate(end));

        service.findEvents(start, end, uris, true);
        verify(repository).findWithUniqueIpByUris(convertToDate(start), convertToDate(end), List.of(uris));
    }
}
