package io.charkchalk.backend.json.place;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlaceJson extends BasePlaceJson {
    private UUID uuid;
}
