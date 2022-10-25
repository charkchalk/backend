package io.charkchalk.backend.service.tag;

import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.json.tag.BaseTagJson;
import io.charkchalk.backend.json.tag.TagConverter;
import io.charkchalk.backend.json.tag.TagJson;
import io.charkchalk.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
public class TagController {

    @Autowired
    private TagConverter tagConverter;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/api/tag")
    public ResponseEntity<TagJson> createTag(@RequestBody @NotNull BaseTagJson baseTagJson) {
        Tag tag = tagConverter.convertToEntity(baseTagJson);
        tagRepository.save(tag);
        return ResponseEntity.ok(tagConverter.convertToJson(tag));
    }

    @GetMapping("/api/tag/{name}")
    public ResponseEntity<TagJson> getTag(@PathVariable @NotNull String name) {
        Optional<Tag> tagOptional = tagRepository.findByName(name);
        return tagOptional.map(value -> ResponseEntity.ok(tagConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/tag")
    public ResponseEntity<TagJson> putTag(@RequestBody @NotNull TagJson tagJson) {
        Optional<Tag> tagOptional = tagRepository.findById(tagJson.getId());
        if (tagOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tag tag = tagOptional.get();
        tagConverter.updateEntity(tag, tagJson);
        tagRepository.save(tag);
        return ResponseEntity.ok(tagConverter.convertToJson(tag));
    }

    @DeleteMapping("/api/tag/{name}")
    public ResponseEntity<Void> deleteTag(@PathVariable @NotNull String name) {
        Optional<Tag> tagOptional = tagRepository.findByName(name);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tagRepository.delete(tagOptional.get());
        return ResponseEntity.ok().build();
    }
}
