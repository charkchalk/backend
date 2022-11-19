package io.charkchalk.backend.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageJson<T> {

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("currentPage")
    private int currentPage;

    @JsonProperty("content")
    private List<T> content = new ArrayList<>();
}
