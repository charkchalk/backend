package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Organization;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
    Optional<Organization> findByName(String name);
    boolean existsByName(String name);
}
