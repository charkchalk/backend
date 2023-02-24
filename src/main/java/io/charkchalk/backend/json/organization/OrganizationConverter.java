package io.charkchalk.backend.json.organization;

import io.charkchalk.backend.entity.Organization;
import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.repository.OrganizationRepository;
import io.charkchalk.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrganizationConverter {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private TagRepository tagRepository;

    public Organization convertToEntity(BaseOrganizationJson baseOrganizationJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();
        if (organizationRepository.existsByName(baseOrganizationJson.getName())) {
            fieldNotValidItems.add(FieldNotValidItem
                    .entityAlreadyExists("name", "Organization", baseOrganizationJson.getName()));
        }

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);

        Organization organization = new Organization();
        this.updateEntity(organization, baseOrganizationJson);
        return organization;
    }

    public OrganizationJson convertToJson(Organization organization) {
        OrganizationJson organizationJson = new OrganizationJson();
        organizationJson.setId(organization.getId());
        organizationJson.setName(organization.getName());
        organizationJson.setDescription(organization.getDescription());

        Collection<String> tags = new HashSet<>();

        for (Tag tag : organization.getTags()) {
            tags.add(tag.getName());
        }

        organizationJson.setTags(tags);
        return organizationJson;
    }

    public PageJson<OrganizationJson> convertToPageJson(Page<Organization> organizations) {
        PageJson<OrganizationJson> pageJson = new PageJson<>();
        pageJson.setTotalPages(organizations.getTotalPages());
        pageJson.setCurrentPage(organizations.getNumber());

        if (!organizations.isEmpty()) {
            for (Organization organization : organizations.getContent()) {
                pageJson.getContent().add(convertToJson(organization));
            }
        }

        return pageJson;
    }

    public void updateEntity(Organization organization, BaseOrganizationJson baseOrganizationJson) {
        organization.setName(baseOrganizationJson.getName());
        organization.setDescription(baseOrganizationJson.getDescription());

        Collection<Tag> tags = new HashSet<>();

        for (String tagName : baseOrganizationJson.getTags()) {
            tagRepository.findByName(tagName).ifPresent(tags::add);
        }

        organization.setTags(tags);
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> possibleProperty = new HashSet<>();
        possibleProperty.add("name");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, possibleProperty, "Organization");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
