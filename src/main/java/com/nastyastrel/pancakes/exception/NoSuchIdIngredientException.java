package com.nastyastrel.pancakes.exception;

public class NoSuchIdIngredientException extends RuntimeException{
    public NoSuchIdIngredientException(String ingredientId) {
        super(String.format(ExceptionMessage.NO_INGREDIENT_ID, ingredientId));
    }
}
