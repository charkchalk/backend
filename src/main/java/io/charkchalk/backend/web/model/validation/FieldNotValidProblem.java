package io.charkchalk.backend.web.model.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.charkchalk.backend.web.model.Problem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FieldNotValidProblem extends Problem {
    @JsonProperty("detail")
    private List<FieldNotValidObject> detail = new ArrayList<>();
}
