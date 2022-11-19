package io.charkchalk.backend.web.model.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldNotValidObject {

    @JsonProperty("field_name")
    private String fieldName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("message")
    private String message;
}
