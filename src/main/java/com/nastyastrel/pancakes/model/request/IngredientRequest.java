package com.nastyastrel.pancakes.model.request;

import com.nastyastrel.pancakes.model.pancake.Category;

public record IngredientRequest(String name, double price, Category category, boolean healthy) {

}
