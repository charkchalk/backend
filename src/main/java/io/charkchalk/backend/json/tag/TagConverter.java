package io.charkchalk.backend.json.tag;

import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.repository.TagRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TagConverter {

    @Autowired
    private TagRepository tagRepository;

    public Tag convertToEntity(@NotNull BaseTagJson baseTagJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();
        if (tagRepository.existsByName(baseTagJson.getName())) {
            fieldNotValidItems.add(FieldNotValidItem.entityAlreadyExists("name", "Tag", baseTagJson.getName()));
        }

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);

        return updateEntity(new Tag(), baseTagJson);
    }

    public TagJson convertToJson(@NotNull Tag tag) {
        TagJson tagJson = new TagJson();
        tagJson.setName(tag.getName());
        tagJson.setUuid(tag.getUuid());
        tagJson.setDescription(tag.getDescription());
        tagJson.setTagLimit(tag.getTagLimit());
        return tagJson;
    }

    public PageJson<TagJson> convertToPageJson(Page<Tag> tags) {
        PageJson<TagJson> pageJson = new PageJson<>();
        pageJson.setTotalPages(tags.getTotalPages());
        pageJson.setCurrentPage(tags.getNumber());

        if (!tags.isEmpty()) {
            for (Tag tag : tags.getContent()) {
                pageJson.getContent().add(convertToJson(tag));
            }
        }

        return pageJson;
    }

    public Tag updateEntity(@NotNull Tag tag, @NotNull BaseTagJson baseTagJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();

        tag.setDescription(baseTagJson.getDescription());

        if (tag.getId() != null) {
            if (!tag.getName().equals(baseTagJson.getName()) || !tag.getTagLimit().equals(baseTagJson.getTagLimit())) {
                if (tagRepository.existsByNameAndTagLimit(baseTagJson.getName(), baseTagJson.getTagLimit())) {
                    fieldNotValidItems.add(FieldNotValidItem.entityAlreadyExists("name/tagLimit", "Tag",
                            baseTagJson.getName() + "/" + baseTagJson.getTagLimit()));
                }
            }
        }

        tag.setName(baseTagJson.getName());
        tag.setTagLimit(baseTagJson.getTagLimit());

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
        return tag;
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> possibleProperty = new HashSet<>();
        possibleProperty.add("name");
        possibleProperty.add("tagLimit");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, possibleProperty, "Tag");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
