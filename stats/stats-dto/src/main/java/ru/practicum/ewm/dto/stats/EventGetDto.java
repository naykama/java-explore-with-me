package ru.practicum.ewm.dto.stats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EventGetDto {
    private final String app;
    private final String uri;
    private final Long  hits;
}
