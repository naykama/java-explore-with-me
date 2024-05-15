package ru.practicum.ewm.entity.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
            super(message);
        }
}
