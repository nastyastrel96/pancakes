package com.nastyastrel.pancakes.exception;

public class NoSuchIdOrderException extends RuntimeException{
    public NoSuchIdOrderException(String orderId) {
        super(String.format(ExceptionMessage.NO_ORDER_ID, orderId));
    }
}
