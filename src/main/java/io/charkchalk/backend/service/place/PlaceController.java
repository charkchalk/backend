package io.charkchalk.backend.service.place;

import io.charkchalk.backend.entity.Place;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.place.BasePlaceJson;
import io.charkchalk.backend.json.place.PlaceConverter;
import io.charkchalk.backend.json.place.PlaceJson;
import io.charkchalk.backend.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Validated
@RestController
public class PlaceController {

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private PlaceRepository placeRepository;

    @PostMapping("/api/place")
    public ResponseEntity<PlaceJson> createPlace(@Valid @RequestBody BasePlaceJson basePlaceJson) {
        Place place = placeConverter.convertToEntity(basePlaceJson);
        placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.convertToJson(place));
    }

    @GetMapping("/api/place")
    public ResponseEntity<PageJson<PlaceJson>> getPlace(Pageable pageable) {
        PlaceConverter.checkPageable(pageable);
        return ResponseEntity.ok(placeConverter.convertToPageJson(placeRepository.findAll(pageable)));
    }

    @GetMapping("/api/place/{id}")
    public ResponseEntity<PlaceJson> getPlace(@PathVariable Long id) {
        Optional<Place> placeOptional = placeRepository.findById(id);
        return placeOptional.map(value -> ResponseEntity.ok(placeConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/place/{id}")
    public ResponseEntity<PlaceJson> putPlace(@PathVariable Long id, @Valid @RequestBody BasePlaceJson basePlaceJson) {
        Optional<Place> placeOptional = placeRepository.findById(id);
        if (placeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Place place = placeOptional.get();
        placeConverter.updateEntity(place, basePlaceJson);
        placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.convertToJson(place));
    }

    @DeleteMapping("/api/place/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        Optional<Place> placeOptional = placeRepository.findById(id);
        if (placeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        placeRepository.delete(placeOptional.get());
        return ResponseEntity.ok().build();
    }
}
