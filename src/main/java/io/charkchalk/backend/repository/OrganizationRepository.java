package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Organization;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
    Optional<Organization> findByUuid(UUID uuid);
    boolean existsByUuid(UUID uuid);
    boolean existsByNameAndParent(String name, Organization parent);
}
