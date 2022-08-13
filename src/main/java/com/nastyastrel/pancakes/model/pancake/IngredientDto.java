package com.nastyastrel.pancakes.model.pancake;


public record IngredientDto(Long ingredientId, String name, double price, Category category, boolean healthy) {
    public static IngredientDto from(Ingredient ingredient) {
        return new IngredientDto(ingredient.getIngredientId(), ingredient.getName(), ingredient.getPrice(), ingredient.getCategory(), ingredient.isHealthy());
    }
}
