package ru.practicum.ewm.entity;

import lombok.*;
import ru.practicum.ewm.entity.enums.StateType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    private String  description;
    @Column(name = "exist_date")
    private LocalDateTime existDate;
    @OneToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @Column(name = "location_lat")
    private float locationLat;
    @Column(name = "location_lon")
    private float locationLon;
    @Column(name = "paid")
    private Boolean isPaid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "request_moderation")
    private boolean isRequestModeration;
    private String  title;
    @Enumerated(EnumType.STRING)
    private StateType state;
    @Column(name = "publish_date")
    private LocalDateTime publishDate;
    private long views;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return id != null && id.equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
