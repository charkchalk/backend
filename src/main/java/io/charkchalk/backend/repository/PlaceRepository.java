package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Place;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlaceRepository extends PagingAndSortingRepository<Place, Long> {
    Optional<Place> findByUuid(UUID uuid);
    boolean existsByNameAndParent(String name, Place parent);
    boolean existsByUuid(UUID uuid);
}
