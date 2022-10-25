package io.charkchalk.backend.json.tag;

import io.charkchalk.backend.entity.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {

    public Tag convertToEntity(@NotNull BaseTagJson baseTagJson) {
        Tag tag = new Tag();
        tag.setName(baseTagJson.getName());
        tag.setDescription(baseTagJson.getDescription());
        tag.setTagLimit(baseTagJson.getTagLimit());
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

    public Tag updateEntity(@NotNull Tag tag, @NotNull TagJson tagJson) {
        tag.setName(tagJson.getName());
        tag.setDescription(tagJson.getDescription());
        tag.setTagLimit(tagJson.getTagLimit());
        return tag;
    }
}
