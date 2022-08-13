package com.nastyastrel.pancakes.exception;

public class NoSuchIdPancakeException extends RuntimeException {
    public NoSuchIdPancakeException(String pancakeId) {
        super(String.format(ExceptionMessage.NO_PANCAKE_ID, pancakeId));
    }
}
