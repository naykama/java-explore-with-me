package ru.practicum.ewm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "views")
@IdClass(ViewId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class View {
    @Id
    @Column(name = "event_id")
    private long eventId;
    @Id
    @Column(name = "user_ip")
    private String userIp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return eventId == view.eventId && Objects.equals(userIp, view.userIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userIp);
    }
}
