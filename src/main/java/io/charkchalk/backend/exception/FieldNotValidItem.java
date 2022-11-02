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
}
