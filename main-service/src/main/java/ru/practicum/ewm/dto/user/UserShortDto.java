package ru.practicum.ewm.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
public class UserShortDto {
    @NotBlank
    private final String email;
    @NotBlank
    private final String name;
}
