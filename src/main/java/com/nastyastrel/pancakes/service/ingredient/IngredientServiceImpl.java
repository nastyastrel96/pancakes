package com.nastyastrel.pancakes.service.ingredient;

import com.nastyastrel.pancakes.exception.NoSuchIdIngredientException;
import com.nastyastrel.pancakes.model.pancake.Ingredient;
import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.model.request.IngredientRequest;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Override
    public List<IngredientDto> findAll() {
        return ingredientRepository.findAll().stream().map(IngredientDto::from).toList();
    }

    @Override
    public IngredientDto findById(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> new NoSuchIdIngredientException(ingredientId.toString()));
        return IngredientDto.from(ingredient);
    }

    @Override
    public void save(IngredientRequest ingredientRequest) {
        Ingredient ingredient = new Ingredient(ingredientRequest.name(), ingredientRequest.price(), ingredientRequest.category(), ingredientRequest.healthy());
        ingredientRepository.save(ingredient);
    }

    @Override
    public IngredientDto updateIngredient(Long ingredientId, IngredientRequest ingredientRequest) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        Ingredient updatedIngredient = ingredientOptional.orElseThrow(() -> new NoSuchIdIngredientException(ingredientId.toString()));
        updatedIngredient.setCategory(ingredientRequest.category());
        updatedIngredient.setName(ingredientRequest.name());
        updatedIngredient.setPrice(ingredientRequest.price());
        updatedIngredient.setHealthy(ingredientRequest.healthy());
        ingredientRepository.save(updatedIngredient);
        return IngredientDto.from(updatedIngredient);
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        Ingredient ingredient = ingredientOptional.orElseThrow(() -> new NoSuchIdIngredientException(ingredientId.toString()));
        ingredientRepository.deleteById(ingredient.getIngredientId());
    }
}

