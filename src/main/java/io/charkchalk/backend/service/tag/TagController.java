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

@Validated
@RestController
public class TagController {

    @Autowired
    private TagConverter tagConverter;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/api/tag")
    public ResponseEntity<TagJson> createTag(@Valid @RequestBody BaseTagJson baseTagJson) {
        Tag tag = tagConverter.convertToEntity(baseTagJson);
        tagRepository.save(tag);
        return ResponseEntity.ok(tagConverter.convertToJson(tag));
    }

    @GetMapping("/api/tag")
    public ResponseEntity<PageJson<TagJson>> listTags(Pageable pageable) {
        TagConverter.checkPageable(pageable);
        return ResponseEntity.ok(tagConverter.convertToJsonPage(tagRepository.findAll(pageable)));
    }

    @GetMapping("/api/tag/{id}")
    public ResponseEntity<TagJson> getTag(@PathVariable @NotNull Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        return tagOptional.map(value -> ResponseEntity.ok(tagConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/tag/{id}")
    public ResponseEntity<TagJson> putTag(@PathVariable @NotNull Long id, @Valid @RequestBody BaseTagJson baseTagJson) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tag tag = tagOptional.get();
        tagConverter.updateEntity(tag, baseTagJson);
        tagRepository.save(tag);
        return ResponseEntity.ok(tagConverter.convertToJson(tag));
    }

    @DeleteMapping("/api/tag/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable @NotNull Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tagRepository.delete(tagOptional.get());
        return ResponseEntity.ok().build();
    }
}
