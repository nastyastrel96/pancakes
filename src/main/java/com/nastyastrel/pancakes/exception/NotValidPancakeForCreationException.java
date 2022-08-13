package com.nastyastrel.pancakes.exception;

public class NotValidPancakeForCreationException extends RuntimeException {
    public NotValidPancakeForCreationException() {
        super(ExceptionMessage.NOT_VALID_PANCAKE_FOR_CREATION);
    }
}
