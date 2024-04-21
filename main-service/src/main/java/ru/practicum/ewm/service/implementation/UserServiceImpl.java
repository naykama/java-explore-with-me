package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.exception.AlreadyExistException;
import ru.practicum.ewm.entity.exception.NotFoundException;
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
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserShortDto user) {
        try {
            return convertToDto(userRepository.save(convertToEntity(user)));
        } catch (DataIntegrityViolationException e) {
            log.error("User with email = {} is already exists", user.getEmail());
            throw new AlreadyExistException(String.format("User with email = %s is already exists", user.getEmail()));
        }
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

    public void deleteUserById(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        }
    }
}
