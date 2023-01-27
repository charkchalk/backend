package io.charkchalk.backend.json.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.net.URL;

@Getter
@Setter
public class BasePersonJson {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private URL link;
}
