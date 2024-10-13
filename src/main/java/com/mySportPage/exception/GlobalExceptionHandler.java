package com.mySportPage.exception;

import com.mySportPage.rest.response.SportPageBaseResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO: add custom Logger to store invalid requests data

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<SportPageBaseResponse> handleValidationExceptions(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.groupingBy(
                        violation -> extractInvalidParamName(violation.getPropertyPath()),
                        Collectors.mapping(this::fillProvidedParamValue, Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(entry -> "field '" + entry.getKey() + "' " + String.join(", ", entry.getValue()))
                .collect(Collectors.joining(", "));

        SportPageBaseResponse response = new SportPageBaseResponse(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SportPageBaseResponse> handleGenericExceptions(Exception ex) {
        SportPageBaseResponse response = new SportPageBaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * As Path object won't allow getting param name from field using violation.getPropertyPath().getCurrentLeafNode(),
     * it is needed to extract it manually.
     */
    private String extractInvalidParamName(Path propertyPath) {
        String invalidFieldName = null;
        for (Path.Node node : propertyPath) {
            invalidFieldName = node.getName();
        }
        return invalidFieldName;
    }

    private String fillProvidedParamValue(ConstraintViolation<?> violation) {
        Object invalidValue = violation.getInvalidValue();
        String invalidValueString = (invalidValue != null) ? " (provided: '" + invalidValue + "')" : "";
        return violation.getMessage() + invalidValueString;
    }
}
