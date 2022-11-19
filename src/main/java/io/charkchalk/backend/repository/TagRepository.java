package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    boolean existsByName(String name);
}
