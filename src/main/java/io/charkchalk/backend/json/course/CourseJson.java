package io.charkchalk.backend.json.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseJson extends BaseCourseJson {
    @JsonProperty("id")
    private UUID uuid;
}
