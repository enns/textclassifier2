package org.ripreal.textclassifier2.storage.web.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<WebControllerException> handleThereIsNoSuchCharacteristicException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new WebControllerException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectClassifierType.class)
    protected ResponseEntity<WebControllerException> handleIncorrectClassifierTypeException() {
        return new ResponseEntity<>(new WebControllerException("Illegal argument \"classifierType\" in options parameters"), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class WebControllerException {
        private String message;
    }
}
