package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.RequestDto;
import ru.practicum.ewm.entity.Request;

public class RequestMapper {
    public static RequestDto convertToDto(Request request) {
        return new RequestDto(request.getId(), request.getEvent().getId(), request.getRequester().getId(),
                request.getStatus(), request.getCreateDate());
    }
}
