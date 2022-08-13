package com.nastyastrel.pancakes.model.report;

import com.nastyastrel.pancakes.model.pancake.Ingredient;

public record Report(Ingredient mostIngredient, Ingredient mostHealthyIngredient) {
}
