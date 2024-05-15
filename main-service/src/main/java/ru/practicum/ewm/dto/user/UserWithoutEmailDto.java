package ru.practicum.ewm.dto.user;

import lombok.Getter;

@Getter
public class UserWithoutEmailDto extends UserBaseDto {
    private final Long id;

    public UserWithoutEmailDto(Long id, String name) {
        super(name);
        this.id = id;
    }
}
