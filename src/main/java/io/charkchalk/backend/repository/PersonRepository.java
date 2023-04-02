package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Person;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    Optional<Person> findByUuid(UUID uuid);
}
