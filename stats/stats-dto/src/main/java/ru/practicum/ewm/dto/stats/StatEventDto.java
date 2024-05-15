package ru.practicum.ewm.dto.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatEventDto {
    @NotBlank(message = "Название сервиса не может быть пустым")
    private String app;
    @NotBlank(message = "Адрес, на который отправлен запрос, не может быть пустым")
    private String uri;
    @NotBlank(message = "Ip отправителя не может быть пустым")
    private String ip;
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime existDate;
}
