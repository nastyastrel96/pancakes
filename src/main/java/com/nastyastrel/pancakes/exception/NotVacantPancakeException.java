package com.nastyastrel.pancakes.exception;

public class NotVacantPancakeException extends RuntimeException {
    public NotVacantPancakeException() {
        super(ExceptionMessage.NOT_VACANT_PANCAKE);
    }
}
