package com.nastyastrel.pancakes.service.ingredient;

import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.model.request.IngredientRequest;

import java.util.List;

public interface IngredientService {
    List<IngredientDto> findAll();

    IngredientDto findById(Long ingredientId);

    void save(IngredientRequest ingredientRequest);

    IngredientDto updateIngredient(Long ingredientId, IngredientRequest ingredientRequest);

    void deleteIngredient(Long ingredientId);
}
