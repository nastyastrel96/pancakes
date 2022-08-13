package com.nastyastrel.pancakes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ControllerAdvice
public class PancakesExceptionHandler {
    @ExceptionHandler(NoSuchIdIngredientException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchIdIngredientException(NoSuchIdIngredientException exception) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchIdPancakeException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchIdPancakeException(NoSuchIdPancakeException exception) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotValidPancakeForOrderException.class)
    public ResponseEntity<ExceptionResponse> handleNotValidPancakeForOrderException(NotValidPancakeForOrderException exception) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidPancakeForCreationException.class)
    public ResponseEntity<ExceptionResponse> handleNotValidPancakeForCreationException(NotValidPancakeForCreationException exception) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidOrderForCreationException.class)
    public ResponseEntity<ExceptionResponse> handleNotValidOrderForCreationException(NotValidOrderForCreationException exception) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchIdOrderException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchIdOrderException(NoSuchIdOrderException exception) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotVacantPancakeException.class)
    public ResponseEntity<ExceptionResponse> handleNotVacantPancakeException(NotVacantPancakeException exception) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
