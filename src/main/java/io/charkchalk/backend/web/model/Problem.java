package io.charkchalk.backend.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Problem {

    @JsonProperty("title")
    private String title;

    @JsonProperty("status")
    private int status;
}
