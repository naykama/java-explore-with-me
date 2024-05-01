package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.utils.annotation.ValidDate;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventShortDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    private CategoryDto category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String  description;
    @JsonProperty("eventDate")
    @NotBlank
    @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d")
    @ValidDate
    private String existDate;
    @NotNull
    private Location location;
    @JsonProperty("paid")
    private boolean isPaid = false;
    @PositiveOrZero
    private int participantLimit = 0;
    @JsonProperty("requestModeration")
    private boolean isRequestModeration = true;
    @NotBlank
    @Size(min = 3, max = 120, message = "String size must be 3 - 120 characters")
    private String  title;

    public EventShortDto(String annotation, Long category, String description, String existDate, Location location,
                         Boolean isPaid, Integer participantLimit, Boolean isRequestModeration, String title) {
        this.annotation = annotation;
        this.category = new CategoryDto(category);
        this.description = description;
        this.existDate = existDate;
        this.location = location;
        this.isPaid = isPaid;
        this.participantLimit = participantLimit;
        this.isRequestModeration = isRequestModeration;
        this.title = title;
    }
}
