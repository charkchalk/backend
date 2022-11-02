package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.range.DateRange;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DateRangeRepository extends PagingAndSortingRepository<DateRange, Long> {
}
