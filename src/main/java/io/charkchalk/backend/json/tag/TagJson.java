package io.charkchalk.backend.json.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;


@Getter
@Setter
public class TagJson extends BaseTagJson {
    @Min(1)
    @JsonProperty("id")
    private Long id;
}
