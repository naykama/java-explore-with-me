package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;
import ru.practicum.ewm.service.CommentService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/users/{userId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{eventId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable long userId, @PathVariable long eventId, @Valid @RequestBody CommentPostDto comment) {
        log.info("Creating comment with text = {}", comment.getText());
        return commentService.createComment(comment, LocalDateTime.now(), eventId, userId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable long userId, @PathVariable long commentId, @Valid @RequestBody CommentPostDto comment) {
        log.info("Updating comment with id = {}", commentId);
        return commentService.updateComment(userId, commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long userId, @PathVariable long commentId) {
        log.info("Deleting comment with id = {}", commentId);
        commentService.deleteComment(userId, commentId);
    }
}
