package io.charkchalk.backend.service.organization;

import io.charkchalk.backend.entity.Organization;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.organization.BaseOrganizationJson;
import io.charkchalk.backend.json.organization.OrganizationConverter;
import io.charkchalk.backend.json.organization.OrganizationJson;
import io.charkchalk.backend.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Validated
@RestController
public class OrganizationController {

    @Autowired
    private OrganizationConverter organizationConverter;

    @Autowired
    private OrganizationRepository organizationRepository;

    @PostMapping("/api/organization")
    public ResponseEntity<OrganizationJson> createOrganization(@Valid @RequestBody BaseOrganizationJson json) {
        Organization organization = organizationConverter.convertToEntity(json);
        organization = organizationRepository.save(organization);
        return ResponseEntity.ok(organizationConverter.convertToJson(organization));
    }

    @GetMapping("/api/organization")
    public ResponseEntity<PageJson<OrganizationJson>> getOrganization(Pageable pageable) {
        OrganizationConverter.checkPageable(pageable);
        return ResponseEntity.ok(organizationConverter.convertToPageJson(organizationRepository.findAll(pageable)));
    }

    @GetMapping("/api/organization/{id}")
    public ResponseEntity<OrganizationJson> getOrganization(@PathVariable Long id){
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        return organizationOptional.map(value -> ResponseEntity.ok(organizationConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/organization/{id}")
    public ResponseEntity<OrganizationJson> putOrganization(@PathVariable Long id,
                                                            @Valid @RequestBody BaseOrganizationJson json) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (organizationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Organization organization = organizationOptional.get();
        organizationConverter.updateEntity(organization, json);
        organizationRepository.save(organization);
        return ResponseEntity.ok(organizationConverter.convertToJson(organization));
    }

    @DeleteMapping("/api/organization/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (organizationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        organizationRepository.delete(organizationOptional.get());
        return ResponseEntity.ok().build();
    }
}
