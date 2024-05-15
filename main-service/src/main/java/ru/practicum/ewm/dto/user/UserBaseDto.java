package ru.practicum.ewm.dto.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//@RequiredArgsConstructor
@Getter
public abstract class UserBaseDto {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 250)
    private final String name;

    public UserBaseDto(String name) {
        this.name = name;
    }
}
