package ru.practicum.ewm.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
public abstract class UserBaseDto {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 250)
    private final String name;
}
