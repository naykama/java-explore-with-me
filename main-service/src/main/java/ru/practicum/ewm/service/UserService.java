package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;

public interface UserService {
    UserDto createUser(UserShortDto user);
}
