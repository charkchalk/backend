package io.charkchalk.backend.service.range.date;

import io.charkchalk.backend.entity.range.DateRange;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.range.date.DateRangeJson;
import io.charkchalk.backend.json.range.date.DateRangeConverter;
import io.charkchalk.backend.repository.DateRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
public class DateController {

    @Autowired
    private DateRangeConverter dateRangeConverter;

    @Autowired
    private DateRangeRepository dateRangeRepository;

    @PostMapping("/api/range/date")
    public ResponseEntity<DateRangeJson> createDateRange(@Valid @RequestBody DateRangeJson dateRangeJson) {
        DateRange dateRange = dateRangeConverter.convertToEntity(dateRangeJson);
        dateRangeRepository.save(dateRange);
        return ResponseEntity.ok(dateRangeConverter.convertToJson(dateRange));
    }

    @GetMapping("/api/range/date")
    public ResponseEntity<PageJson<DateRangeJson>> listDateRanges(Pageable pageable) {
        DateRangeConverter.checkPageable(pageable);
        return ResponseEntity.ok(dateRangeConverter.convertToPageJson(dateRangeRepository.findAll(pageable)));
    }

    @GetMapping("/api/range/date/{name}")
    public ResponseEntity<DateRangeJson> getDateRange(@PathVariable @NotNull String name) {
        Optional<DateRange> dateRangeOptional = dateRangeRepository.findByName(name);
        return dateRangeOptional.map(value -> ResponseEntity.ok(dateRangeConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/range/date/{name}")
    public ResponseEntity<DateRangeJson> putDateRange(@PathVariable @NotNull String name,
                                                      @Valid @RequestBody DateRangeJson dateRangeJson) {
        Optional<DateRange> dateRangeOptional = dateRangeRepository.findByName(name);
        if (dateRangeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DateRange dateRange = dateRangeOptional.get();
        dateRangeConverter.updateEntity(dateRange, dateRangeJson);
        dateRangeRepository.save(dateRange);
        return ResponseEntity.ok(dateRangeConverter.convertToJson(dateRange));
    }

    @DeleteMapping("/api/range/date/{name}")
    public ResponseEntity<Void> deleteDateRange(@PathVariable @NotNull String name) {
        Optional<DateRange> dateRangeOptional = dateRangeRepository.findByName(name);
        if (dateRangeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        dateRangeRepository.delete(dateRangeOptional.get());
        return ResponseEntity.ok().build();
    }
}
