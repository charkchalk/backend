package io.charkchalk.backend.json.range.date;

import io.charkchalk.backend.entity.range.DateRange;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.repository.DateRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DateRangeConverter {

    @Autowired
    private DateRangeRepository dateRangeRepository;

    public DateRange convertToEntity(DateRangeJson dateRangeJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();
        if (dateRangeRepository.existsByName(dateRangeJson.getName())) {
            fieldNotValidItems.add(FieldNotValidItem
                    .entityAlreadyExists("name", "DateRange", dateRangeJson.getName()));
        }

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);

        return updateEntity(new DateRange(), dateRangeJson);
    }

    public DateRangeJson convertToJson(DateRange dateRange) {
        DateRangeJson dateRangeJson = new DateRangeJson();
        dateRangeJson.setName(dateRange.getName());
        dateRangeJson.setStartDate(dateRange.getStartDate());
        dateRangeJson.setEndDate(dateRange.getEndDate());
        return dateRangeJson;
    }

    public PageJson<DateRangeJson> convertToPageJson(Page<DateRange> dateRanges) {
        PageJson<DateRangeJson> pageJson = new PageJson<>();
        pageJson.setTotalPages(dateRanges.getTotalPages());
        pageJson.setCurrentPage(dateRanges.getNumber());

        if (!dateRanges.isEmpty()) {
            for (DateRange dateRange : dateRanges.getContent()) {
                pageJson.getContent().add(convertToJson(dateRange));
            }
        }

        return pageJson;
    }

    public DateRange updateEntity(DateRange dateRange, DateRangeJson dateRangeJson) {
        dateRange.setName(dateRangeJson.getName());
        dateRange.setStartDate(dateRangeJson.getStartDate());
        dateRange.setEndDate(dateRangeJson.getEndDate());
        return dateRange;
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> validSorts = new HashSet<>();
        validSorts.add("name");
        validSorts.add("startDate");
        validSorts.add("endDate");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, validSorts, "DateRange");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
