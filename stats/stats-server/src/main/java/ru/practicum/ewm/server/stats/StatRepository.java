package ru.practicum.ewm.server.stats;

import org.springframework.data.repository.CrudRepository;

public interface StatRepository extends CrudRepository<Event, Long> {
}
