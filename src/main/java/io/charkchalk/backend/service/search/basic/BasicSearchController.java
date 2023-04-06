package io.charkchalk.backend.service.search.basic;

import io.charkchalk.backend.entity.Organization;
import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.organization.OrganizationConverter;
import io.charkchalk.backend.json.organization.OrganizationJson;
import io.charkchalk.backend.json.person.PersonConverter;
import io.charkchalk.backend.json.person.PersonJson;
import io.charkchalk.backend.json.place.PlaceConverter;
import io.charkchalk.backend.json.place.PlaceJson;
import io.charkchalk.backend.json.tag.TagConverter;
import io.charkchalk.backend.json.tag.TagJson;
import io.charkchalk.backend.repository.search.OrganizationSearchRepository;
import io.charkchalk.backend.repository.search.PersonSearchRepository;
import io.charkchalk.backend.repository.search.PlaceSearchRepository;
import io.charkchalk.backend.repository.search.TagSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Validated
@RestController
public class BasicSearchController {

    @Autowired
    private TagSearchRepository tagSearchRepository;

    @Autowired
    private OrganizationSearchRepository organizationSearchRepository;

    @Autowired
    private PersonSearchRepository personSearchRepository;

    @Autowired
    private PlaceSearchRepository placeSearchRepository;

    @Autowired
    private TagConverter tagConverter;

    @Autowired
    private OrganizationConverter organizationConverter;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private PlaceConverter placeConverter;

    @GetMapping("/api/search/basic/tag/{name}")
        public ResponseEntity<PageJson<TagJson>> getTag(@PathVariable @NotNull String name, Pageable pageable) {
        TagConverter.checkPageable(pageable);
        Page<Tag> tagPage = tagSearchRepository.searchKeyword(name, pageable);
        return ResponseEntity.ok(tagConverter.convertToPageJson(tagPage));
    }

    @GetMapping("/api/search/basic/organization/{name}")
    public ResponseEntity<PageJson<OrganizationJson>> getOrganization(@PathVariable @NotNull String name,
                                                                      Pageable pageable) {
        TagConverter.checkPageable(pageable);
        Page<Organization> organizationPage = organizationSearchRepository.searchKeyword(name, pageable);
        return ResponseEntity.ok(organizationConverter.convertToPageJson(organizationPage));
    }

    @GetMapping("/api/search/basic/person/{name}")
    public ResponseEntity<PageJson<PersonJson>> getPerson(@PathVariable @NotNull String name, Pageable pageable) {
        TagConverter.checkPageable(pageable);
        Page<io.charkchalk.backend.entity.Person> personPage = personSearchRepository.searchKeyword(name, pageable);
        return ResponseEntity.ok(personConverter.convertToPageJson(personPage));
    }

    @GetMapping("/api/search/basic/place/{name}")
    public ResponseEntity<PageJson<PlaceJson>> getPlace(@PathVariable @NotNull String name, Pageable pageable) {
        TagConverter.checkPageable(pageable);
        Page<io.charkchalk.backend.entity.Place> placePage = placeSearchRepository.searchKeyword(name, pageable);
        return ResponseEntity.ok(placeConverter.convertToPageJson(placePage));
    }
}
