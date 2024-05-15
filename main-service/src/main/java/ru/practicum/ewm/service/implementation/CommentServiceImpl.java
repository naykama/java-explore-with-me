package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;
import ru.practicum.ewm.entity.Comment;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.exception.ConflictException;
import ru.practicum.ewm.entity.exception.NotFoundException;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.CommentService;

import java.time.LocalDateTime;

import static ru.practicum.ewm.mapper.CommentMapper.convertToDto;
import static ru.practicum.ewm.mapper.CommentMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public CommentDto createComment(CommentPostDto dto, LocalDateTime createTime, long eventId, long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id = {} not found", userId);
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        }
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Event with id = {} not found", eventId);
            return new NotFoundException(String.format("Event with id = %d not found", eventId));
            }
        );
        return convertToDto(commentRepository.save(convertToEntity(dto, createTime, event, userId)));
    }

    public CommentDto updateComment(long userId, long commentId, CommentPostDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
                log.error("Comment with id = {} not found", commentId);
                return new NotFoundException(String.format("Comment with id = %d not found", commentId));
            }
        );
        if (userId != comment.getUserId()) {
            log.error("User is not an author of comment");
            throw new ConflictException("User is not an author of comment");
        }
        comment.setText(dto.getText());
        comment.setCreateDate(LocalDateTime.now());
        return convertToDto(commentRepository.save(comment));
    }
}
