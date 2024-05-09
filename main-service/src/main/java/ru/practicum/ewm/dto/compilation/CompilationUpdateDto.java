package ru.practicum.ewm.dto.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CompilationUpdateDto {
    @JsonProperty("events")
    List<Long> eventIds;
    @JsonProperty("pinned")
    Boolean isPinned = false;
    @Size(min = 1, max = 50)
    String title;
}
