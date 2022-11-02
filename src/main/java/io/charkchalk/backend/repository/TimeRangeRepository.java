package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.range.TimeRange;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TimeRangeRepository extends PagingAndSortingRepository<TimeRange, Long> {
}
