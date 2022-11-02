package io.charkchalk.backend.repository;

import io.charkchalk.backend.entity.Branch;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BranchRepository extends PagingAndSortingRepository<Branch, Long> {
}
