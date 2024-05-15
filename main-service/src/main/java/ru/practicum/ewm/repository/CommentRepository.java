package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
