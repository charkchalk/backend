package io.charkchalk.backend.json.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BasePlaceJson {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("description")
    private String description;

    @NotBlank
    @JsonProperty("parent_slug")
    private String parentSlug;
}
