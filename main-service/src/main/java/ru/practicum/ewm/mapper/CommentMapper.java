package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;
import ru.practicum.ewm.entity.Comment;
import ru.practicum.ewm.entity.Compilation;
import ru.practicum.ewm.entity.Event;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

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
}
