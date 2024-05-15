package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll(Pageable pageable);

    List<User> findByIdIn(List<Long> ids, Pageable pageable);
}
