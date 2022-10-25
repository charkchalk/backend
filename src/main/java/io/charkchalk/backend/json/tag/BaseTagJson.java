package io.charkchalk.backend.json.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.charkchalk.backend.entity.enums.TagLimit;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BaseTagJson {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("description")
    private String description;

    @JsonProperty("limit")
    private TagLimit tagLimit;
}
