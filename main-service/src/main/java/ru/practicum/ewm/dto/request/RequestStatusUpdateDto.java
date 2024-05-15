package ru.practicum.ewm.dto.request;

import lombok.*;
import ru.practicum.ewm.entity.enums.RequestType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RequestStatusUpdateDto {
    List<Long> requestIds;
    RequestType status;
}
