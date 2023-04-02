package io.charkchalk.backend.json.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TagJson extends BaseTagJson {
    private UUID uuid;
}
