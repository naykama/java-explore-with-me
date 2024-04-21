package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.mapper.UserMapper.convertToDto;
import static ru.practicum.ewm.mapper.UserMapper.convertToEntity;
import static ru.practicum.ewm.utils.CustomPage.getPage;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserShortDto user) {
        return convertToDto(userRepository.save(convertToEntity(user)));
    }

    public List<UserDto> findUsers(Long[] ids, int from, int size) {
        Pageable pageConfig = getPage(from, size);
        if (ids == null) {
           return userRepository.findAll(pageConfig).stream()
                   .map(UserMapper::convertToDto)
                   .collect(Collectors.toList());
        } else {
           return userRepository.findByIdIn(Arrays.asList(ids), pageConfig).stream()
                   .map(UserMapper::convertToDto)
                   .collect(Collectors.toList());
        }
    }
}
