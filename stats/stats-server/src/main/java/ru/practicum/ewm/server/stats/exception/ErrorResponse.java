package ru.practicum.ewm.server.stats.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String error;
    private String stackTrace;

    public ErrorResponse(String error, String stackTrace) {
        this.error = error;
        this.stackTrace = stackTrace;
    }
}