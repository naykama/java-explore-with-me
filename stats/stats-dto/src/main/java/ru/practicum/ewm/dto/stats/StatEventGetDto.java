package ru.practicum.ewm.dto.stats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class StatEventGetDto {
    @NotBlank
    private final String app;
    @NotBlank
    private final String uri;
    @NotNull
    private final Long  hits;
}
