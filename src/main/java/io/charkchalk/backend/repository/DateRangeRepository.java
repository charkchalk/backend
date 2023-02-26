package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.range.DateRange;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DateRangeRepository extends PagingAndSortingRepository<DateRange, Long> {
    Optional<DateRange> findByName(String name);
    boolean existsByName(String name);
}
