package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.UserService;

import static ru.practicum.ewm.mapper.UserMapper.convertToDto;
import static ru.practicum.ewm.mapper.UserMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserShortDto user) {
        return convertToDto(userRepository.save(convertToEntity(user)));
    }
}
