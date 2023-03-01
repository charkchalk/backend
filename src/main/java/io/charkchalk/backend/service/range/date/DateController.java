package io.charkchalk.backend.service.range.date;

import io.charkchalk.backend.entity.range.DateRange;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.range.date.BaseDateRangeJson;
import io.charkchalk.backend.json.range.date.DateRangeConverter;
import io.charkchalk.backend.json.range.date.DateRangeJson;
import io.charkchalk.backend.repository.DateRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DateController {

    @Autowired
    private DateRangeConverter dateRangeConverter;

    @Autowired
    private DateRangeRepository dateRangeRepository;

    @PostMapping("/api/range/date")
    public ResponseEntity<BaseDateRangeJson> createDateRange(@Valid @RequestBody BaseDateRangeJson baseDateRangeJson) {
        DateRange dateRange = dateRangeConverter.convertToEntity(baseDateRangeJson);
        dateRangeRepository.save(dateRange);
        return ResponseEntity.ok(dateRangeConverter.convertToJson(dateRange));
    }

    @GetMapping("/api/range/date")
    public ResponseEntity<PageJson<DateRangeJson>> listDateRanges(Pageable pageable) {
        DateRangeConverter.checkPageable(pageable);
        return ResponseEntity.ok(dateRangeConverter.convertToPageJson(dateRangeRepository.findAll(pageable)));
    }

    @GetMapping("/api/range/date/{uuid}")
    public ResponseEntity<DateRangeJson> getDateRange(@PathVariable @NotNull UUID uuid) {
        Optional<DateRange> dateRangeOptional = dateRangeRepository.findByUuid(uuid);
        return dateRangeOptional.map(value -> ResponseEntity.ok(dateRangeConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/range/date/{uuid}")
    public ResponseEntity<BaseDateRangeJson> putDateRange(@PathVariable @NotNull UUID uuid,
                                                          @Valid @RequestBody BaseDateRangeJson baseDateRangeJson) {
        Optional<DateRange> dateRangeOptional = dateRangeRepository.findByUuid(uuid);
        if (dateRangeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DateRange dateRange = dateRangeOptional.get();
        dateRangeConverter.updateEntity(dateRange, baseDateRangeJson);
        dateRangeRepository.save(dateRange);
        return ResponseEntity.ok(dateRangeConverter.convertToJson(dateRange));
    }

    @DeleteMapping("/api/range/date/{uuid}")
    public ResponseEntity<Void> deleteDateRange(@PathVariable @NotNull UUID uuid) {
        Optional<DateRange> dateRangeOptional = dateRangeRepository.findByUuid(uuid);
        if (dateRangeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        dateRangeRepository.delete(dateRangeOptional.get());
        return ResponseEntity.ok().build();
    }
}
