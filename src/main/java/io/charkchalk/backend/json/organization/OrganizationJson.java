package io.charkchalk.backend.json.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;


@Getter
@Setter
public class OrganizationJson extends BaseOrganizationJson {
    @Min(1)
    @JsonProperty("id")
    private Long id;
}
