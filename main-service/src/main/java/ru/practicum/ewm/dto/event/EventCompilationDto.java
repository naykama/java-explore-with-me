package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.utils.annotation.ValidDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EventCompilationDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    @JsonProperty("eventDate")
    @NotBlank
    @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d")
    @ValidDate
    private String existDate;
    long id;
    UserShortDto initiator;
    @JsonProperty("paid")
    private boolean isPaid = false;
    @NotBlank
    @Size(min = 3, max = 120, message = "String size must be 3 - 120 characters")
    private String  title;
    private long views = 0L;
    private List<CommentPostDto> comments;
}
