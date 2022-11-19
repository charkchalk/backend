package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
}
