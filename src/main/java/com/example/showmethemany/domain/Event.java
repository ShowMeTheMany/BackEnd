package com.example.showmethemany.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EVENT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
    private Integer discountRate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;

    public void updateEvent(String eventName) {
        this.eventName = eventName;
    }

    public void updateEvent(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public void updateEvent(Integer discountRate) {
        this.discountRate = discountRate;
    }

    public void updateStartTime(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    public void updateEndTime(LocalDateTime endAt) {
        this.endAt = endAt;
    }
}
