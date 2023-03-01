package io.charkchalk.backend.json.organization;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrganizationJson extends BaseOrganizationJson {
    private UUID uuid;
}
