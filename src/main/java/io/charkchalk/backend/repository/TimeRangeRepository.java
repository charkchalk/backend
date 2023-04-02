package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.enums.Weekday;
import io.charkchalk.backend.entity.range.TimeRange;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface TimeRangeRepository extends PagingAndSortingRepository<TimeRange, Long> {
    Optional<TimeRange> findByStartTimeAndEndTimeAndWeekday(LocalTime startTime, LocalTime endTime, Weekday weekday);

    boolean existsByStartTimeAndEndTimeAndWeekday(LocalTime startTime, LocalTime endTime, Weekday weekday);
}
