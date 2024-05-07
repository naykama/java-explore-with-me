package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.request.RequestDto;
import ru.practicum.ewm.entity.Request;

import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToString;

public class RequestMapper {
    public static RequestDto convertToDto(Request request) {
        return new RequestDto(request.getId(), request.getEvent().getId(), request.getRequester().getId(),
                request.getStatus(), convertToString(request.getCreateDate()));
    }
}
