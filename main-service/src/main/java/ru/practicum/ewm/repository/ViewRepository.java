package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.View;
import ru.practicum.ewm.entity.ViewId;

public interface ViewRepository extends CrudRepository<View, ViewId> {
    long countByEventId(long evenId);

    boolean existsByEventIdAndUserIp(long eventId, String clientIp);
}
