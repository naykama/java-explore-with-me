package ru.practicum.ewm.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.dto.RequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestService {
    RequestDto createRequest(long userId, long eventId, LocalDateTime createDate);

    List<RequestDto> findAll(long userId);

    RequestDto cancelRequest(long userId, long requestId);
}
