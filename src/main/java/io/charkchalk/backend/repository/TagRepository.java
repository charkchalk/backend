package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.entity.enums.TagLimit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    Optional<Tag> findByUuid(UUID uuid);
    boolean existsByNameAndTagLimit(String name, TagLimit tagLimit);
    boolean existsByName(String name);
}
