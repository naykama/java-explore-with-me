package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.User;

public class UserMapper {
    public static User convertToEntity(UserShortDto user) {
        return new User(user.getEmail(), user.getName());
    }

    public static UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }
}
