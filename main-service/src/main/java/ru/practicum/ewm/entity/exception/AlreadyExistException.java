package ru.practicum.ewm.entity.exception;

public class AlreadyExistException extends ConflictException {
    public AlreadyExistException(String message) {
        super(message);
    }
}
