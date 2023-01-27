package io.charkchalk.backend.entity.range;

import io.charkchalk.backend.entity.Course;
import io.charkchalk.backend.entity.enums.Weekday;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

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

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Enumerated
    @Column(name = "weekday")
    private Weekday weekday;

    @ManyToMany(mappedBy = "timeRanges")
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();
}
