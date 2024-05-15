package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;

import java.time.LocalDateTime;

public interface CommentService {
    CommentDto createComment(CommentPostDto dto, LocalDateTime createTime, long eventId, long userId);
}
