package com.nastyastrel.pancakes.exception;

public class NotValidOrderForCreationException extends RuntimeException {
    public NotValidOrderForCreationException() {
        super(ExceptionMessage.NOT_VALID_ORDER_FOR_CREATION);
    }
}
