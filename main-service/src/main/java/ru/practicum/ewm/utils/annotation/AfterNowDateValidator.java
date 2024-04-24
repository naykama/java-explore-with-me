package ru.practicum.ewm.utils.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.practicum.ewm.dto.stats.utils.ConvertDate.convertToDate;

public class AfterNowDateValidator implements ConstraintValidator<ValidDate, String> {
    private static long MAX_DURATION = SECONDS.convert(2, HOURS);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        LocalDateTime existDate = convertToDate(value);
        return existDate.isAfter(LocalDateTime.now()) && !existDate.minusHours(2).isBefore(LocalDateTime.now());
    }
}
