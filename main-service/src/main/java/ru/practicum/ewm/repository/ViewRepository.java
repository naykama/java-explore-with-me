package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.View;
import ru.practicum.ewm.entity.ViewId;

import java.util.List;

public interface ViewRepository extends CrudRepository<View, ViewId> {
    long countByEventId(long evenId);

    boolean existsByEventIdAndUserIp(long eventId, String clientIp);

    List<View> findAllByEventIdIn(List<Long> eventIds);
}
