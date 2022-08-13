package com.nastyastrel.pancakes.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime localDateTime, String message) {

}
