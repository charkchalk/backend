package io.charkchalk.backend.json.tag;

import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.repository.TagRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        if(tagRepository.existsByName(baseTagJson.getName())){
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

    public void updateEntity(@NotNull Tag tag, @NotNull TagJson tagJson) {
        tag.setName(tagJson.getName());
        tag.setDescription(tagJson.getDescription());
        tag.setTagLimit(tagJson.getTagLimit());
    }
}
