package io.charkchalk.backend.json.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;


@Getter
@Setter
public class PlaceJson extends BasePlaceJson {
    @Min(1)
    @JsonProperty("id")
    private Long id;
}
