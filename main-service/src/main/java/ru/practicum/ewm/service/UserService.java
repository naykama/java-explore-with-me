package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserShortDto user);

    List<UserDto> findUsers(Long[] ids, int from, int size);

    void deleteUserById(long userId);
}
