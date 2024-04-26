package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByInitiatorId(long useId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);

    boolean existsByIdAndInitiatorId(long eventId, long userId);
}
