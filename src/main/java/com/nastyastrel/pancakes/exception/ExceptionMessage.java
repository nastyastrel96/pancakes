package com.nastyastrel.pancakes.exception;

public class ExceptionMessage {
    public static final String NO_INGREDIENT_ID = "Ingredient with id = %s is not found";
    public static final String NO_PANCAKE_ID = "Pancake with id = %s is not found";
    public static final String NOT_VALID_PANCAKE_FOR_ORDER = "Pancake with id = %s is not valid. Valid pancake should have only 1 ingredient as the base and at least 1 ingredient as a filling";
    public static final String NOT_VALID_PANCAKE_FOR_CREATION = "Pancake is not valid for creation. Valid pancake should have at least 1 ingredient";
    public static final String NOT_VALID_ORDER_FOR_CREATION = "Order is not valid for creation. Valid order should have at least 1 pancake";
    public static final String NO_ORDER_ID = "Order with id = %s is not found";
    public static final String NOT_VACANT_PANCAKE = "At first you need to create pancakes and then to add them to the order";
}
