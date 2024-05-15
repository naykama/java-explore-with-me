package ru.practicum.ewm.dto.comment;

import lombok.*;
import ru.practicum.ewm.entity.Event;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CommentDto {
    private long id;
    private long eventId;
    private long userId;
    private String text;
    private LocalDateTime createDate;
}
