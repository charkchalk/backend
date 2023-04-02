package io.charkchalk.backend.json.range.date;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DateRangeJson extends BaseDateRangeJson {
    private UUID uuid;
}
