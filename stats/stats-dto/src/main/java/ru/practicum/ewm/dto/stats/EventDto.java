package ru.practicum.ewm.dto.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EventDto {
    private String app;
    private String uri;
    private String ip;
    @JsonProperty("timestamp")
    private String existDate;
}
