package ru.practicum.ewm.server.stats.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StatEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime existDate;

    public StatEvent(String app, String uri, String ip, LocalDateTime existDate) {
        this.id = -1L;
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.existDate = existDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatEvent)) return false;
        return id != null && id.equals(((StatEvent) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
