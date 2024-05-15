package ru.practicum.ewm.server.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.dto.stats.StatEventDto;
import ru.practicum.ewm.server.stats.entity.StatEvent;
import ru.practicum.ewm.server.stats.service.StatService;
import ru.practicum.ewm.server.stats.service.StatServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        StatEventDto event = new StatEventDto("ewm-main-service", "/events/1", "192.163.0.1", LocalDateTime.now());
        when(repository.save(EventStatMapper.convertToEntity(event))).thenReturn(EventStatMapper.convertToEntity(event));
        service.saveEvent(event);
        verify(repository).save(any(StatEvent.class));
    }

    @Test
    public void findEventTest() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(5);
        String[] uris = new String[] {"uri1", "uri2"};
        when(repository.findWithNoUniqueIp(start, end)).thenReturn(new ArrayList<>());
        when(repository.findWithNoUniqueIpByUris(start, end, List.of(uris))).thenReturn(new ArrayList<>());
        when(repository.findWithUniqueIp(start, end)).thenReturn(new ArrayList<>());
        when(repository.findWithUniqueIpByUris(start, end, List.of(uris))).thenReturn(new ArrayList<>());

        service.findEvents(start, end, null, false);
        verify(repository).findWithNoUniqueIp(start, end);

        service.findEvents(start, end,uris, false);
        verify(repository).findWithNoUniqueIpByUris(start, end, List.of(uris));

        service.findEvents(start, end, null, true);
        verify(repository).findWithUniqueIp(start, end);

        service.findEvents(start, end, uris, true);
        verify(repository).findWithUniqueIpByUris(start, end, List.of(uris));
    }
}
