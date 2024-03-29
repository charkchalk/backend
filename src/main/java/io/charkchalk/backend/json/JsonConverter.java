package io.charkchalk.backend.json;

import io.charkchalk.backend.exception.FieldNotValidException;
import io.charkchalk.backend.exception.FieldNotValidItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class JsonConverter {

    private static final int DEFAULT_RANDOM_STRING_LENGTH = 8;

    public static void checkFieldNotValidException(List<FieldNotValidItem> fieldNotValidItems) {
        if (!fieldNotValidItems.isEmpty()) {
            throw new FieldNotValidException(fieldNotValidItems);
        }
    }

    public static List<FieldNotValidItem> checkPageableSortProperty(Pageable pageable, Set<String> possibleProperties,
                                                                    String entityName) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();

        if (pageable.getSort().isSorted()) {
            Sort sort = pageable.getSort();

            for (Sort.Order order: sort) {
                if (!possibleProperties.contains(order.getProperty())) {
                    fieldNotValidItems.add(FieldNotValidItem.sortPropertyNotFound(order.getProperty(), entityName));
                }
            }
        }

        return fieldNotValidItems;
    }

    public static String generateSlug(String name) {
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, DEFAULT_RANDOM_STRING_LENGTH);
        return name.toLowerCase().replace(" ", "-") + "-" + randomString;
    }
}
