package ru.practicum.ewm.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;

import java.time.LocalDateTime;

public interface CommentService {
    CommentDto createComment(CommentPostDto dto, LocalDateTime createTime, long eventId, long userId);

    CommentDto updateComment(long userId, long commentId, CommentPostDto comment);

    void deleteComment(long userId, long commentId);
}
