package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.enums.StateType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByInitiatorId(long useId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);

    @Query("SELECT event FROM Event event\n" +
            "JOIN FETCH event.initiator as init\n" +
            "JOIN FETCH event.category as cat\n" +
            "WHERE (COALESCE(:users) IS NULL OR init.id IN :users) AND\n" +
            "(COALESCE(:states) IS NULL OR event.state IN :states) AND\n" +
            "(COALESCE(:categories) IS NULL OR cat.id IN :categories) AND\n" +
            "(CAST(:rangeStart AS timestamp) is null OR event.existDate >= :rangeStart) AND\n" +
            "(CAST(:rangeEnd AS timestamp) is null OR event.existDate <= :rangeEnd)")
    List<Event> findAllByParams(@Param("users") List<Long> users,
                                @Param("states") List<StateType> states,
                                @Param("categories")  List<Long> categories,
                                @Param("rangeStart") LocalDateTime rangeStart,
                                @Param("rangeEnd") LocalDateTime rangeEnd,
                                Pageable pageable);

    boolean existsByCategoryId(long categoryId);

    @Query("SELECT event FROM Event event\n" +
            "JOIN FETCH event.category as cat\n" +
            "WHERE (:text IS NULL OR LOWER(event.annotation) LIKE CONCAT('%', LOWER(:text), '%') OR LOWER(event.description) LIKE CONCAT('%', LOWER(:text), '%')) AND\n" +
            "(COALESCE(:categories) IS NULL OR cat.id IN :categories) AND\n" +
            "(:isPaid IS NULL OR event.isPaid = :isPaid) AND\n" +
            "(event.existDate >= :rangeStart) AND\n" +
            "(CAST(:rangeEnd AS timestamp) is null OR event.existDate <= :rangeEnd) AND\n" +
            "(:isOnlyAvailable = false OR event.participantLimit > 0) AND\n" +
            "(event.state = 'PUBLISHED')")
    List<Event> findAllForPublic(
                                @Param("text") String text,
                                @Param("categories") List<Long> categories,
                                @Param("isPaid") Boolean isPaid,
                                @Param("rangeStart") LocalDateTime rangeStart,
                                @Param("rangeEnd") LocalDateTime rangeEnd,
                                @Param("isOnlyAvailable") boolean isOnlyAvailable,
                                Pageable pageable);

    Optional<Event> findByIdAndState(long eventId, StateType state);

    List<Event> findAllByIdIn(List<Long> eventIds);
}
