package io.charkchalk.backend.json.tag;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;


@Getter
@Setter
public class TagJson extends BaseTagJson {
    @Min(1)
    private Long id;
}
