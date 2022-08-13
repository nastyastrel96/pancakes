package com.nastyastrel.pancakes.exception;

public class NotValidPancakeForOrderException extends RuntimeException {
    public NotValidPancakeForOrderException(String pancakeId) {
        super(String.format(ExceptionMessage.NOT_VALID_PANCAKE_FOR_ORDER, pancakeId));
    }
}
