package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.RequestDto;

import java.time.LocalDateTime;

public interface RequestService {
    RequestDto createRequest(long userId, long eventId, LocalDateTime createDate);
}
