package io.charkchalk.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FieldNotValidException extends RuntimeException {

    private List<FieldNotValidItem> fieldNotValidItems;

}
