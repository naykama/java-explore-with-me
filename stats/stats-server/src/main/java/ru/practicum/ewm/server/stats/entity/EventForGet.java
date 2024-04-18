package ru.practicum.ewm.server.stats.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventForGet {
    private String app;
    private String uri;
    private long count;
}
