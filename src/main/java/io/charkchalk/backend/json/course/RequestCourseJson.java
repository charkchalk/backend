package io.charkchalk.backend.json.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.charkchalk.backend.json.range.time.BaseTimeRangeJson;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.util.UUID;

@Data
public class RequestCourseJson {

    @JsonProperty("code")
    private String code;

    @JsonProperty("predecessor_uuid")
    private UUID predecessorUuid;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("organization_uuid")
    private UUID organizationUuid;

    @JsonProperty("link")
    private URL link;

    @JsonProperty("credit")
    private Integer credit;

    @JsonProperty("date_range_uuid")
    private UUID dateRangeUuid;

    @JsonProperty("tags_uuid")
    private UUID[] tagsUuid = new UUID[0];

    @JsonProperty("places_uuid")
    private UUID[] placesUuid = new UUID[0];

    @JsonProperty("time_ranges")
    private BaseTimeRangeJson[] timeRanges = new BaseTimeRangeJson[0];

    @JsonProperty("hosts_uuid")
    private UUID[] hostsUuid = new UUID[0];
}
