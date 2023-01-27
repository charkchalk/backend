package io.charkchalk.backend.json.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.UUID;

@Getter
@Setter
public class PersonJson extends BasePersonJson {
    @Min(1)
    @JsonProperty("id")
    private UUID id;
}
