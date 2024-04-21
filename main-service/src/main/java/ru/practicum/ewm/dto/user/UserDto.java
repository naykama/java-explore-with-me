package ru.practicum.ewm.dto.user;

import lombok.Getter;

@Getter
public class UserDto extends UserShortDto {
    private final long id;

    public UserDto(long id, String email, String name) {
        super(email, name);
        this.id  = id;
    }
}
