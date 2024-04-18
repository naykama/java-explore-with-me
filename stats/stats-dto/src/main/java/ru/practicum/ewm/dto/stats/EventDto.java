package ru.practicum.ewm.dto.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EventDto {
    private final String app;
    private final String uri;
    private final String ip;
    @JsonProperty("timestamp")
    private final String existDate;
}