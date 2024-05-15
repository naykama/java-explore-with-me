package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentPostDto;
import ru.practicum.ewm.service.CommentService;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/users/{userId}/comments/{eventId}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable long userId, @PathVariable long eventId, @RequestBody CommentPostDto comment) {
        log.info("Creating comment with text = {}", comment.getText());
        return commentService.createComment(comment, LocalDateTime.now(), eventId, userId);
    }
}
