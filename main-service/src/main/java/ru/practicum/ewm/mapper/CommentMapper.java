package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;
import ru.practicum.ewm.dto.comment.CommentShortDto;
import ru.practicum.ewm.entity.Comment;
import ru.practicum.ewm.entity.Event;

import java.time.LocalDateTime;

public class CommentMapper {
    public static Comment convertToEntity(CommentPostDto dto, LocalDateTime createDate, Event event, long userId) {
        return Comment.builder()
                .text(dto.getText())
                .createDate(createDate)
                .event(event)
                .userId(userId)
                .build();
    }

    public static CommentDto convertToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getEvent().getId(), comment.getUserId(), comment.getText(), comment.getCreateDate());
    }

    public static CommentShortDto convertToShortDto(Comment comment) {
        return new CommentShortDto(comment.getUserId(), comment.getText(), comment.getCreateDate());
    }
}
