package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.user.UserWithoutEmailDto;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.enums.StateType;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EventDto extends EventShortDto {
    private long id;
    private CategoryDto category;
    @JsonProperty("createdOn")
    private String createDate;
    private UserWithoutEmailDto initiator;
    private StateType state;
////    private int confirmedRequests;
    @JsonProperty("publishedOn")
    private String publishDate;
//    private long views;

    public EventDto(String annotation, long id, CategoryDto category, String createDate, String description,
                    String existDate, UserWithoutEmailDto initiator, Location location, boolean isPaid,
                    int participantLimit, boolean isRequestModeration, String title, StateType state, String publishDate) {
        super(annotation, category.getId(), description, existDate, location, isPaid, participantLimit,
                                                                            isRequestModeration, title);
        this.category = category;
        this.id = id;
        this.createDate = createDate;
        this.initiator = initiator;
        this.state = state;
        this.publishDate = publishDate;
////        this.confirmedRequests = confirmedRequests;
////        this.views = views;
    }
}
