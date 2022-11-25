package io.charkchalk.backend.json.range.date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;

@Getter
@Setter
public class DateRangeJson extends BaseDateRangeJson {
    @Min(1)
    @JsonProperty("id")
    private Long id;
}
