package ru.practicum.ewm.dto.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.ewm.dto.event.EventCompilationDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CompilationDto {
    long id;
    List<EventCompilationDto> events;
    @JsonProperty("pinned")
    boolean isPinned;
    String title;
}
