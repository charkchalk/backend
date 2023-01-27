package io.charkchalk.backend.json.range.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.charkchalk.backend.entity.enums.Weekday;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
public class BaseTimeRangeJson {

    @NotNull
    @JsonProperty("start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(implementation = String.class, example = "08:00")
    private LocalTime startTime;

    @NotNull
    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(implementation = String.class, example = "21:00")
    private LocalTime endTime;

    @NotNull
    @JsonProperty("week")
    private Weekday weekday;
}
