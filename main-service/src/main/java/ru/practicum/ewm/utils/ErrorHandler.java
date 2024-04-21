package ru.practicum.ewm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.entity.exception.AlreadyExistException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({AlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistException(final AlreadyExistException e) {
        log.error("Error 409 {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }
}
