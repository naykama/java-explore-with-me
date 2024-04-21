package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserShortDto user) {
        log.info("Creating user, email={}, name={}", user.getEmail(), user.getName());
        return userService.createUser(user);
    }

    @GetMapping
    public List<UserDto> findUsers(@RequestParam(required = false) Long[] ids,
                                   @RequestParam(defaultValue = "0") int from,
                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Finding users");
        return userService.findUsers(ids, from, size);
    }
}
