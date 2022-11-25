package io.charkchalk.backend.json.range.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class TimeRangeJson extends BaseTimeRangeJson {
    @Min(1)
    @JsonProperty("id")
    private Long id;
}
