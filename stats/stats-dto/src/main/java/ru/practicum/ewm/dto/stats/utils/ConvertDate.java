package ru.practicum.ewm.dto.stats.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDate {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime convertToDate(String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public static String convertToString(LocalDateTime date) {
        return date.format(FORMATTER);
    }
}
