package io.charkchalk.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "date_range")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DateRange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // OneToMany
    @OneToMany(mappedBy = "dateRange")
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();
}