package ru.practicum.ewm.server.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.stats.EventDto;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository repository;

    @Override
    public void saveEvent(EventDto eventDto) {
        repository.save(EventMapper.convertToEntity(eventDto));
    }
}
