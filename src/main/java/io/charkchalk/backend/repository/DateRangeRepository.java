package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.range.DateRange;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface DateRangeRepository extends PagingAndSortingRepository<DateRange, Long> {
    Optional<DateRange> findByName(String name);
    Optional<DateRange> findByUuid(UUID uuid);
    boolean existsByName(String name);
}
