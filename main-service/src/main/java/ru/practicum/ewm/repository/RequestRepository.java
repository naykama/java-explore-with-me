package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Request;
import ru.practicum.ewm.entity.enums.RequestType;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long> {
    boolean existsByEventIdAndRequesterId(long eventId, long requestId);

    int countByEventIdAndStatus(long eventId, RequestType type);

    List<Request> findAllByRequesterId(long userId);

    List<Request> findAllByEventIdAndEventInitiatorId(long eventId, long userId);

    List<Request> findAllByEventIdInAndStatus(List<Long> eventIds, RequestType requestType);
}
