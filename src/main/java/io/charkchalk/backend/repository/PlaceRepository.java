package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Place;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlaceRepository extends PagingAndSortingRepository<Place, Long> {
}
