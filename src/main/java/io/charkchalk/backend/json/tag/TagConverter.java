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
import org.springframework.data.domain.Sort;
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
        Tag tag = new Tag();
        tag.setName(baseTagJson.getName());
        tag.setDescription(baseTagJson.getDescription());
        tag.setTagLimit(baseTagJson.getTagLimit());

        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();

        if (tagRepository.existsByName(baseTagJson.getName())) {
            fieldNotValidItems.add(FieldNotValidItem.entityAlreadyExists("name", "Tag", baseTagJson.getName()));
        }

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
        return tag;
    }

    public TagJson convertToJson(@NotNull Tag tag) {
        TagJson tagJson = new TagJson();
        tagJson.setId(tag.getId());
        tagJson.setName(tag.getName());
        tagJson.setDescription(tag.getDescription());
        tagJson.setTagLimit(tag.getTagLimit());
        return tagJson;
    }

    public PageJson<TagJson> convertToJsonPage(Page<Tag> tags) {
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

    public void updateEntity(@NotNull Tag tag, @NotNull BaseTagJson baseTagJson) {
        tag.setName(baseTagJson.getName());
        tag.setDescription(baseTagJson.getDescription());
        tag.setTagLimit(baseTagJson.getTagLimit());
    }

    public static void checkPageable(Pageable pageable) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();

        if (pageable.getSort().isSorted()) {
            Sort sort = pageable.getSort();

            Set<String> possibleProperty = new HashSet<>();
            possibleProperty.add("name");
            possibleProperty.add("tagLimit");

            for (Sort.Order order: sort) {
                if (!possibleProperty.contains(order.getProperty())) {
                    fieldNotValidItems.add(FieldNotValidItem.sortPropertyNotFound(order.getProperty(), "Tag"));
                }
            }
        }

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
