package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.user.UserWithoutEmailDto;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.StateType;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EventDto extends EventShortDto {
    private long id;
    private CategoryDto category;
    @JsonProperty("createdOn")
    private String createDate;
    private UserWithoutEmailDto initiator;
////    private int confirmedRequests;
//    @JsonProperty("publishedOn")
//    private String publishDate;
//    private long views;

    public EventDto(String annotation, long id, CategoryDto category, String createDate, String description,
                    String existDate, UserWithoutEmailDto initiator, Location location, boolean isPaid,
                    int participantLimit, boolean isRequestModeration, String title, StateType state
//                     String publishDate
    ) {
        super(annotation, category.getId(), description, existDate, location, isPaid, participantLimit,
                                                                            isRequestModeration, title);
        this.category = category;
        this.id = id;
        this.createDate = createDate;
        this.initiator = initiator;
//                category.getId(), description, existDate, location, isPaid, participantLimit, isRequestModeration, title);
////        this.confirmedRequests = confirmedRequests;
//        this.createDate = createDate;
//        this.publishDate = publishDate;
////        this.initiator = initiator;
//        this.state = state;
////        this.views = views;
//        this.id = id;
//        this.category = category;
    }
}
