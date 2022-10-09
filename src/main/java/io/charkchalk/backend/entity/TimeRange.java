package io.charkchalk.backend.entity;

import io.charkchalk.backend.entity.enums.Week;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.TimeZone;

@Entity
@Table(name = "time_range")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TimeRange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "timezone", nullable = false)
    private TimeZone timeZone;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Enumerated
    @Column(name = "week")
    private Week week;
}