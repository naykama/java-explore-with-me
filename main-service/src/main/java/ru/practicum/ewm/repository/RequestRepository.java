package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Request;
import ru.practicum.ewm.entity.enums.RequestType;

public interface RequestRepository extends CrudRepository<Request, Long> {
    boolean existsByEventIdAndRequesterId(long eventId, long requestId);

    int countByEventIdAndStatusNot(long eventId, RequestType type);
}
