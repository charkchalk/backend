package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.enums.Weekday;
import io.charkchalk.backend.entity.range.TimeRange;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalTime;

public interface TimeRangeRepository extends PagingAndSortingRepository<TimeRange, Long> {
    boolean existsByStartTimeAndEndTimeAndWeek(LocalTime startTime, LocalTime endTime, Weekday weekday);
}
