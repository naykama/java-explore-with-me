package ru.practicum.ewm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.practicum.ewm.entity.enums.RequestType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class RequestDto {
    private Long id;
    @JsonProperty("event")
    private long eventId;
    @JsonProperty("requester")
    private long requesterId;
    private RequestType status;
    @JsonProperty("created")
    private String createDate;
}
