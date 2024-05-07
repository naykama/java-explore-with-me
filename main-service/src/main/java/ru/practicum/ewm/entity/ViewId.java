package ru.practicum.ewm.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class ViewId implements Serializable {
    private long eventId;
    private String userIp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewId viewId = (ViewId) o;
        return eventId == viewId.eventId && Objects.equals(userIp, viewId.userIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userIp);
    }
}
