package io.charkchalk.backend.service.tag;

import io.charkchalk.backend.entity.Tag;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.tag.BaseTagJson;
import io.charkchalk.backend.json.tag.TagConverter;
import io.charkchalk.backend.json.tag.TagJson;
import io.charkchalk.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
public class TagController {

    @Autowired
    private TagConverter tagConverter;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/api/tag")
    public ResponseEntity<BaseTagJson> createTag(@Valid @RequestBody BaseTagJson baseTagJson) {
        Tag tag = tagConverter.convertToEntity(baseTagJson);
        tagRepository.save(tag);
        return ResponseEntity.ok(tagConverter.convertToJson(tag));
    }

    @GetMapping("/api/tag")
    public ResponseEntity<PageJson<TagJson>> listTags(Pageable pageable) {
        TagConverter.checkPageable(pageable);
        return ResponseEntity.ok(tagConverter.convertToPageJson(tagRepository.findAll(pageable)));
    }

    @GetMapping("/api/tag/{uuid}")
    public ResponseEntity<TagJson> getTag(@PathVariable @NotNull UUID uuid) {
        Optional<Tag> tagOptional = tagRepository.findByUuid(uuid);
        return tagOptional.map(value -> ResponseEntity.ok(tagConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/tag/{uuid}")
    public ResponseEntity<TagJson> putTag(@PathVariable @NotNull UUID uuid,
                                          @Valid @RequestBody BaseTagJson baseTagJson) {
        Optional<Tag> tagOptional = tagRepository.findByUuid(uuid);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tag tag = tagOptional.get();
        tagConverter.updateEntity(tag, baseTagJson);
        tagRepository.save(tag);
        return ResponseEntity.ok(tagConverter.convertToJson(tag));
    }

    @DeleteMapping("/api/tag/{uuid}")
    public ResponseEntity<Void> deleteTag(@PathVariable @NotNull UUID uuid) {
        Optional<Tag> tagOptional = tagRepository.findByUuid(uuid);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tagRepository.delete(tagOptional.get());
        return ResponseEntity.ok().build();
    }
}
