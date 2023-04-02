package io.charkchalk.backend.json.person;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PersonJson extends BasePersonJson {
    private UUID uuid;
}
