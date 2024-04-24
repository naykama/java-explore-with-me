package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByInitiatorId(long useId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);
}
