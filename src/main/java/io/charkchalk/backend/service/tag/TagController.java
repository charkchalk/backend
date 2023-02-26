package io.charkchalk.backend.service.tag;

import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.tag.TagJson;
import io.charkchalk.backend.json.tag.TagConverter;
import io.charkchalk.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
@RestController
public class TagController {

    @Autowired
    private TagConverter tagConverter;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/api/tag")
    public ResponseEntity<TagJson> createTag(@Valid @RequestBody TagJson tagJson) {
        Tag tag = tagConverter.convertToEntity(tagJson);
        tagRepository.save(tag);
        return ResponseEntity.ok(tagConverter.convertToJson(tag));
    }

    @GetMapping("/api/tag")
    public ResponseEntity<PageJson<TagJson>> listTags(Pageable pageable) {
        TagConverter.checkPageable(pageable);
        return ResponseEntity.ok(tagConverter.convertToPageJson(tagRepository.findAll(pageable)));
    }

    @GetMapping("/api/tag/{name}")
    public ResponseEntity<TagJson> getTag(@PathVariable @NotNull String name) {
        Optional<Tag> tagOptional = tagRepository.findByName(name);
        return tagOptional.map(value -> ResponseEntity.ok(tagConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/tag/{name}")
    public ResponseEntity<TagJson> putTag(@PathVariable @NotNull String name, @Valid @RequestBody TagJson tagJson) {
        Optional<Tag> tagOptional = tagRepository.findByName(name);
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
