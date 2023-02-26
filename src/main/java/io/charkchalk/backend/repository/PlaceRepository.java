package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Place;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PlaceRepository extends PagingAndSortingRepository<Place, Long> {
    Optional<Place> findBySlug(String slug);
    boolean existsByNameAndParent(String name, Place parent);
    boolean existsBySlug(String slug);
}
