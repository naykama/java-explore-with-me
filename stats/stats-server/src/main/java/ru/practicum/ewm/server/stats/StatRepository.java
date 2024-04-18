package ru.practicum.ewm.server.stats;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends CrudRepository<Event, Long> {
    @Query("SELECT new ru.practicum.ewm.server.stats.EventForGet(event.app, event.uri, COUNT(ip))\n" +
            "FROM Event as event\n" +
            "WHERE event.existDate BETWEEN ?1 AND ?2\n" +
            "GROUP BY event.app, event.uri")
    List<EventForGet> findWithNoUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.ewm.server.stats.EventForGet(event.app, event.uri, COUNT(DISTINCT ip))\n" +
           "FROM Event as event\n" +
           "WHERE event.existDate BETWEEN ?1 AND ?2\n" +
           "GROUP BY event.app, event.uri")
    List<EventForGet> findWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.ewm.server.stats.EventForGet(event.app, event.uri, COUNT(ip))\n" +
            "FROM Event as event\n" +
            "WHERE event.existDate BETWEEN :start AND :end AND event.uri IN (:uris)\n" +
            "GROUP BY event.app, event.uri")
    List<EventForGet> findWithNoUniqueIpByUris(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.ewm.server.stats.EventForGet(event.app, event.uri, COUNT(DISTINCT ip))\n" +
            "FROM Event as event\n" +
            "WHERE event.existDate BETWEEN :start AND :end AND event.uri IN (:uris)\n" +
            "GROUP BY event.app, event.uri")
    List<EventForGet> findWithUniqueIpByUris(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end, @Param("uris")List<String> uris);
}