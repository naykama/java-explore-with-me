package ru.practicum.ewm.utils.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Target({ METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AfterNowDateValidator.class })
public @interface ValidDate {
    String message() default "Incorrect event date";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
