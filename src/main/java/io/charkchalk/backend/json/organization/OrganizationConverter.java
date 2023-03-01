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
        return updateEntity(new Organization(), baseOrganizationJson);
    }

    public OrganizationJson convertToJson(Organization organization) {
        OrganizationJson organizationJson = new OrganizationJson();
        organizationJson.setName(organization.getName());
        organizationJson.setUuid(organization.getUuid());
        organizationJson.setDescription(organization.getDescription());
        organizationJson.setParentUuid(organization.getParent().getUuid());

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

    public Organization updateEntity(Organization organization, BaseOrganizationJson baseOrganizationJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();

        organization.setDescription(baseOrganizationJson.getDescription());

//      Check field organization's name or parent member is changed in baseOrganizationJson
        if (!organization.getName().equals(baseOrganizationJson.getName()) ||
                !organization.getParent().getUuid().equals(baseOrganizationJson.getParentUuid())) {
            Optional<Organization> newParent = organizationRepository.findByUuid(baseOrganizationJson.getParentUuid());

            if (newParent.isEmpty()) {
                fieldNotValidItems.add(FieldNotValidItem
                        .entityNotFound("parentUuid", "Organization",
                                baseOrganizationJson.getParentUuid().toString()));
            } else {
//              Check if organization's name and parent are unique
                if (organizationRepository.existsByNameAndParent(baseOrganizationJson.getName(), newParent.get())) {
                    fieldNotValidItems.add(FieldNotValidItem.entityAlreadyExists("name/parent", "Organization",
                            baseOrganizationJson.getName() + "/" + baseOrganizationJson.getParentUuid().toString()));
                }

                organization.setName(baseOrganizationJson.getName());
                organization.setParent(newParent.get());
            }
        }

        Collection<Tag> tags = new HashSet<>();
        for (String tagName : baseOrganizationJson.getTags()) {
            Optional<Tag> tag = tagRepository.findByName(tagName);
            if (tag.isEmpty()) {
                fieldNotValidItems.add(FieldNotValidItem.entityNotFound("tags", "Tag", tagName));
            } else {
                tags.add(tag.get());
            }
        }
        organization.setTags(tags);

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
        return organization;
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> possibleProperty = new HashSet<>();
        possibleProperty.add("name");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, possibleProperty, "Organization");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
