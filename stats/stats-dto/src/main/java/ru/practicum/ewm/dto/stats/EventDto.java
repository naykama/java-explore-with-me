package ru.practicum.ewm.dto.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@RequiredArgsConstructor
public class EventDto {
    @NotBlank(message = "Название сервиса не может быть пустым")
    private final String app;
    @NotBlank(message = "Адрес, на который отправлен запрос, не может быть пустым")
    private final String uri;
    @NotBlank(message = "Ip отправителя не может быть пустым")
    private final String ip;
    @JsonProperty("timestamp")
    @NotBlank(message = "Дата и время запроса должны быть указаны в формате yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d",
            message = "Формат даты не соответствует шаблону yyyy-MM-dd HH:mm:ss")
    private final String existDate;
}
