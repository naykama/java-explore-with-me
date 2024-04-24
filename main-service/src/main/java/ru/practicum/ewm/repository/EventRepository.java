package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
}
