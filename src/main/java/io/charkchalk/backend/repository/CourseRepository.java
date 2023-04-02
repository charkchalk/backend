package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
    Optional<Course> findByUuid(UUID uuid);
    boolean existsByUuid(UUID uuid);
}
