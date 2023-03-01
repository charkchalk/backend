package io.charkchalk.backend.json.place;

import io.charkchalk.backend.entity.Place;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlaceConverter {

    @Autowired
    private PlaceRepository placeRepository;

    public Place convertToEntity(BasePlaceJson basePlaceJson) {
        return updateEntity(new Place(), basePlaceJson);
    }

    public PlaceJson convertToJson(Place place) {
        PlaceJson placeJson = new PlaceJson();
        placeJson.setName(place.getName());
        placeJson.setUuid(place.getUuid());
        placeJson.setDescription(place.getDescription());
        placeJson.setParentUUID(place.getParent() != null ? place.getParent().getUuid() : null);
        return placeJson;
    }

    public PageJson<PlaceJson> convertToPageJson(Page<Place> places) {
        PageJson<PlaceJson> pageJson = new PageJson<>();
        pageJson.setTotalPages(places.getTotalPages());
        pageJson.setCurrentPage(places.getNumber());

        if (!places.isEmpty()) {
            for (Place place : places.getContent()) {
                pageJson.getContent().add(convertToJson(place));
            }
        }
        return pageJson;
    }

    public Place updateEntity(Place place, BasePlaceJson basePlaceJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();

//      Name is not updatable
        if (place.getName() != null && !place.getName().equals(basePlaceJson.getName())) {
            if (placeRepository.existsByNameAndParent(basePlaceJson.getName(), place.getParent())) {
                fieldNotValidItems.add(FieldNotValidItem
                        .entityFieldNotUpdatable("name", "Place"));
            }
        }

        place.setName(basePlaceJson.getName());

//      Name and parent are constraint unique
        if (basePlaceJson.getParentUUID() != null) {
            Optional<Place> parent = placeRepository.findByUuid(basePlaceJson.getParentUUID());
            if (parent.isEmpty()) {
                fieldNotValidItems.add(FieldNotValidItem
                        .entityNotFound("parentSlug", "Place",
                                basePlaceJson.getParentUUID().toString()));
            } else {
                if (placeRepository.existsByNameAndParent(basePlaceJson.getName(), parent.get())) {
                    fieldNotValidItems.add(FieldNotValidItem
                            .entityAlreadyExists("name", "Place", basePlaceJson.getName()));
                } else {
                    place.setParent(parent.get());
                }
            }
        } else {
            if (placeRepository.existsByNameAndParent(basePlaceJson.getName(), null)) {
                fieldNotValidItems.add(FieldNotValidItem
                        .entityAlreadyExists("name", "Place", basePlaceJson.getName()));
            }
        }

        place.setDescription(basePlaceJson.getDescription());
        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
        return place;
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> possibleProperty = new HashSet<>();
        possibleProperty.add("name");
        possibleProperty.add("parent");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, possibleProperty, "Place");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
