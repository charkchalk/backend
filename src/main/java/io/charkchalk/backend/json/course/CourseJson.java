package io.charkchalk.backend.json.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.charkchalk.backend.json.organization.OrganizationJson;
import io.charkchalk.backend.json.person.PersonJson;
import io.charkchalk.backend.json.place.PlaceJson;
import io.charkchalk.backend.json.range.date.DateRangeJson;
import io.charkchalk.backend.json.range.time.BaseTimeRangeJson;
import io.charkchalk.backend.json.tag.TagJson;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CourseJson {
    @JsonProperty("id")
    private UUID uuid;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("organization")
    private OrganizationJson organization;

    @JsonProperty("link")
    private URL link;

    @PositiveOrZero
    @JsonProperty("credits")
    private Integer credit;

    @JsonProperty("date_range")
    private DateRangeJson dateRange;

    @JsonProperty("tags")
    private List<TagJson> tags = new ArrayList<>();

    @JsonProperty("place")
    private List<PlaceJson> places = new ArrayList<>();

    @JsonProperty("time_range")
    private List<BaseTimeRangeJson> timeRanges = new ArrayList<>();

    @JsonProperty("hosts")
    private List<PersonJson> hosts = new ArrayList<>();

}
