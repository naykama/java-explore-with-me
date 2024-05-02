package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.utils.annotation.ValidDate;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventBaseUpdateDto {
    @Size(min = 20, max = 2000)
    private String annotation;
    private CategoryDto category;
    @Size(min = 20, max = 7000)
    private String  description;
    @JsonProperty("eventDate")
    @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d")
    @ValidDate
    private String existDate;
    private Location location;
    @JsonProperty("paid")
    private Boolean isPaid;
    @Positive
    private Integer participantLimit;
    @JsonProperty("requestModeration")
    private Boolean isRequestModeration;
    @Size(min = 3, max = 120, message = "String size must be 3 - 120 characters")
    private String  title;
}
