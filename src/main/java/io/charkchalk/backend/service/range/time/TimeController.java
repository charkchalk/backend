package io.charkchalk.backend.service.range.time;

import io.charkchalk.backend.entity.range.TimeRange;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.range.time.BaseTimeRangeJson;
import io.charkchalk.backend.json.range.time.TimeRangeConverter;
import io.charkchalk.backend.json.range.time.TimeRangeJson;
import io.charkchalk.backend.repository.TimeRangeRepository;
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
public class TimeController {

    @Autowired
    private TimeRangeConverter timeRangeConverter;

    @Autowired
    private TimeRangeRepository timeRangeRepository;

    @PostMapping("/api/range/time")
    public ResponseEntity<TimeRangeJson> createTimeRange(@Valid @RequestBody BaseTimeRangeJson baseTimeRangeJson) {
        TimeRange timeRange = timeRangeConverter.convertToEntity(baseTimeRangeJson);
        timeRangeRepository.save(timeRange);
        return ResponseEntity.ok(timeRangeConverter.convertToJson(timeRange));
    }

    @GetMapping("/api/range/time")
    public ResponseEntity<PageJson<TimeRangeJson>> listTimeRanges(Pageable pageable) {
        TimeRangeConverter.checkPageable(pageable);
        return ResponseEntity.ok(timeRangeConverter.convertToJsonPage(timeRangeRepository.findAll(pageable)));
    }

    @GetMapping("/api/range/time/{id}")
    public ResponseEntity<TimeRangeJson> getTimeRange(@PathVariable @NotNull Long id) {
        return timeRangeRepository.findById(id)
                .map(value -> ResponseEntity.ok(timeRangeConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/range/time/{id}")
    public ResponseEntity<TimeRangeJson> putTimeRange(@PathVariable @NotNull Long id,
                                                      @Valid @RequestBody BaseTimeRangeJson baseTimeRangeJson) {
        Optional<TimeRange> timeRangeOptional = timeRangeRepository.findById(id);
        if (timeRangeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TimeRange timeRange = timeRangeOptional.get();
        timeRangeConverter.updateEntity(timeRange, baseTimeRangeJson);
        timeRangeRepository.save(timeRange);
        return ResponseEntity.ok(timeRangeConverter.convertToJson(timeRange));
    }

    @DeleteMapping("/api/range/time/{id}")
    public ResponseEntity<Void> deleteTimeRange(@PathVariable @NotNull Long id) {
        Optional<TimeRange> timeRangeOptional = timeRangeRepository.findById(id);
        if (timeRangeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        timeRangeRepository.delete(timeRangeOptional.get());
        return ResponseEntity.ok().build();
    }
}
