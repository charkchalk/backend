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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PlaceConverter {

    @Autowired
    private PlaceRepository placeRepository;

    public Place convertToEntity(BasePlaceJson basePlaceJson) {
        Place place = new Place();
        place.setName(basePlaceJson.getName());
        place.setDescription(basePlaceJson.getDescription());
        place.setParent(placeRepository.findById(basePlaceJson.getParent()).orElse(null));
        return place;
    }

    public PlaceJson convertToJson(Place place) {
        PlaceJson placeJson = new PlaceJson();
        placeJson.setId(place.getId());
        placeJson.setName(place.getName());
        placeJson.setDescription(place.getDescription());
        placeJson.setParent(place.getParent().getId());
        return placeJson;
    }

    public PageJson<PlaceJson> convertToJsonPage(Page<Place> places) {
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

    public void updateEntity(Place place, BasePlaceJson basePlaceJson) {
        place.setName(basePlaceJson.getName());
        place.setDescription(basePlaceJson.getDescription());
        place.setParent(placeRepository.findById(basePlaceJson.getParent()).orElse(null));
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> possibleProperty = new HashSet<>();
        possibleProperty.add("name");
        possibleProperty.add("parent");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, possibleProperty);

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
