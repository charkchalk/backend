package io.charkchalk.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldNotValidItem {

    private String fieldName;

    private FieldNotValidType type;

    private String message;

    public static FieldNotValidItem entityNotFound(String fieldName, String entity, String id) {
        String message = String.format("Entity %s with id %s not found", entity, id);
        return new FieldNotValidItem(fieldName, FieldNotValidType.EntityNotFound, message);
    }

    public static FieldNotValidItem entityAlreadyExists(String fieldName, String entity, String id) {
        String message = String.format("Entity %s with %s %s already exists", entity, fieldName, id);
        return new FieldNotValidItem(fieldName, FieldNotValidType.EntityAlreadyExists, message);
    }

    public static FieldNotValidItem sortPropertyNotFound(String property, String entity) {
        String message = String.format("No property %s found in entity %s", property, entity);
        return new FieldNotValidItem("sort", FieldNotValidType.SortPropertyNotFound, message);
    }

    public static FieldNotValidItem entityFieldNotUpdatable(String fieldName, String entity) {
        String message = String.format("Field %s of entity %s is not updatable", fieldName, entity);
        return new FieldNotValidItem(fieldName, FieldNotValidType.FieldNotUpdatable, message);
    }
}
