package io.charkchalk.backend.json.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class BaseOrganizationJson {
    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("description")
    private String description;

    @JsonProperty("parent_uuid")
    private UUID parentUuid;

    @JsonProperty("tags")
    private Collection<String> tags;
}
