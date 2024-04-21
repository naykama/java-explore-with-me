package ru.practicum.ewm.dto.user;

import lombok.Getter;

@Getter
public class UserDto extends UserShortDto {
    private final Long id;

    public UserDto(Long id, String email, String name) {
        super(email, name);
        this.id  = id;
    }
}
