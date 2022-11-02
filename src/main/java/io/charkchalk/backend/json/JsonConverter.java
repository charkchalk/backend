package io.charkchalk.backend.json;

import io.charkchalk.backend.exception.FieldNotValidException;
import io.charkchalk.backend.exception.FieldNotValidItem;

import java.util.List;

public class JsonConverter {
    public static void checkFieldNotValidException(List<FieldNotValidItem> fieldNotValidItems) {
        if (!fieldNotValidItems.isEmpty()) {
            throw new FieldNotValidException(fieldNotValidItems);
        }
    }
}
