package io.charkchalk.backend.web;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.charkchalk.backend.exception.FieldNotValidException;
import io.charkchalk.backend.web.model.Problem;
import io.charkchalk.backend.web.model.SimpleProblem;
import io.charkchalk.backend.web.model.validation.FieldNotValidObject;
import io.charkchalk.backend.web.model.validation.FieldNotValidProblem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldNotValidProblem problem = new FieldNotValidProblem();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            FieldNotValidObject fieldNotValidObject = new FieldNotValidObject();
            fieldNotValidObject.setFieldName(fieldError.getField());
            fieldNotValidObject.setType(fieldError.getCode());
            fieldNotValidObject.setMessage(fieldError.getDefaultMessage());

            problem.getDetail().add(fieldNotValidObject);
        });

        problem.setTitle("Validation error");
        problem.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FieldNotValidException.class)
    public ResponseEntity<Problem> handleFieldNotValidException(FieldNotValidException e) {
        FieldNotValidProblem problem = new FieldNotValidProblem();

        e.getFieldNotValidItems().forEach(fieldNotValidItem -> {
            FieldNotValidObject fieldNotValidObject = new FieldNotValidObject();
            fieldNotValidObject.setFieldName(fieldNotValidItem.getFieldName());
            fieldNotValidObject.setType(fieldNotValidItem.getType().name());
            fieldNotValidObject.setMessage(fieldNotValidItem.getMessage());

            problem.getDetail().add(fieldNotValidObject);
        });

        problem.setTitle("Validation error");
        problem.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Problem> handleHttpMessageNotReadableException(InvalidFormatException e) {

        SimpleProblem problem = new SimpleProblem();

        problem.setTitle("Invalid format");
        problem.setStatus(HttpStatus.BAD_REQUEST.value());
        problem.setDetail(e.getMessage());

        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Problem> handleDataIntegrityViolationException(SQLException e) {
        SimpleProblem problem = new SimpleProblem();

        problem.setTitle("Database error");
        problem.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        problem.setDetail(e.getMessage());

        return new ResponseEntity<>(problem, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
