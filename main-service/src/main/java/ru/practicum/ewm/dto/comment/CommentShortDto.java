package ru.practicum.ewm.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CommentShortDto {
    private long userId;
    private String text;
    private LocalDateTime createDate;
}
