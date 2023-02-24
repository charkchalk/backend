package io.charkchalk.backend.json.range.time;

import io.charkchalk.backend.entity.range.TimeRange;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.repository.TimeRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TimeRangeConverter {

    @Autowired
    private TimeRangeRepository timeRangeRepository;

    public TimeRange convertToEntity(BaseTimeRangeJson baseTimeRangeJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();
        if (timeRangeRepository.existsByStartTimeAndEndTimeAndWeekday(
                baseTimeRangeJson.getStartTime(),
                baseTimeRangeJson.getEndTime(),
                baseTimeRangeJson.getWeekday())) {

            fieldNotValidItems.add(FieldNotValidItem
                    .entityAlreadyExists("whole entity",
                            "TimeRange",
                            "startTime: " + baseTimeRangeJson.getStartTime()
                                    + ", endTime: " + baseTimeRangeJson.getEndTime()
                                    + ", week: " + baseTimeRangeJson.getWeekday()));
        }

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);

        return updateEntity(new TimeRange(), baseTimeRangeJson);
    }

    public TimeRangeJson convertToJson(TimeRange timeRange) {
        TimeRangeJson timeRangeJson = new TimeRangeJson();
        timeRangeJson.setId(timeRange.getId());
        timeRangeJson.setStartTime(timeRange.getStartTime());
        timeRangeJson.setEndTime(timeRange.getEndTime());
        timeRangeJson.setWeekday(timeRange.getWeekday());
        return timeRangeJson;
    }

    public PageJson<TimeRangeJson> convertToPageJson(Page<TimeRange> timeRanges) {
        PageJson<TimeRangeJson> pageJson = new PageJson<>();
        pageJson.setTotalPages(timeRanges.getTotalPages());
        pageJson.setCurrentPage(timeRanges.getNumber());

        if (!timeRanges.isEmpty()) {
            for (TimeRange timeRange : timeRanges.getContent()) {
                pageJson.getContent().add(convertToJson(timeRange));
            }
        }

        return pageJson;
    }

    public TimeRange updateEntity(TimeRange timeRange, BaseTimeRangeJson baseTimeRangeJson) {
        timeRange.setStartTime(baseTimeRangeJson.getStartTime());
        timeRange.setEndTime(baseTimeRangeJson.getEndTime());
        timeRange.setWeekday(baseTimeRangeJson.getWeekday());
        return timeRange;
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> validSorts = new HashSet<>();
        validSorts.add("timeZone");
        validSorts.add("startTime");
        validSorts.add("endTime");
        validSorts.add("week");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, validSorts, "TimeRange");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
