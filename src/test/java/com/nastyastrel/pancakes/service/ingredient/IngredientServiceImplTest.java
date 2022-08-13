package com.nastyastrel.pancakes.service.ingredient;

import com.nastyastrel.pancakes.exception.NoSuchIdIngredientException;
import com.nastyastrel.pancakes.model.pancake.Category;
import com.nastyastrel.pancakes.model.pancake.Ingredient;
import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.model.request.IngredientRequest;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(ingredientRepository);
    }

    @Test
    void itShouldFindAllIngredientsAndReturnIngredientsDto() {
        //given
        List<Ingredient> ingredients = List.of(
                new Ingredient(1L, "Egg", 100.0, Category.BASE, true),
                new Ingredient(2L, "Peach", 200.0, Category.FRUIT, true)
        );
        List<IngredientDto> ingredientsDto = ingredients.stream().map(IngredientDto::from).toList();
        when(ingredientRepository.findAll()).thenReturn(ingredients);

        //when
        var answer = ingredientService.findAll();

        //then
        assertEquals(ingredientsDto, answer);
    }

    @Test
    void itShouldFindIngredientByIdAndReturnIngredientDtoIfIngredientIdIsCorrect() {
        //given
        Ingredient ingredient = new Ingredient(1L, "Egg", 100.0, Category.BASE, true);
        IngredientDto ingredientDto = IngredientDto.from(ingredient);
        when(ingredientRepository.findById(eq(ingredient.getIngredientId()))).thenReturn(Optional.of(ingredient));

        //when
        var answer = ingredientService.findById(1L);

        //then
        assertEquals(ingredientDto, answer);
    }

    @Test
    void itShouldThrowExceptionIfIngredientIdIsIncorrect() {
        //given
        Ingredient ingredient = new Ingredient(1L, "Egg", 100.0, Category.BASE, true);
        when(ingredientRepository.findById(eq(ingredient.getIngredientId()))).thenReturn(Optional.empty());

        //then
        Throwable exception = assertThrows(NoSuchIdIngredientException.class, () -> ingredientService.findById(1L));
        assertEquals("Ingredient with id = 1 is not found", exception.getMessage());
    }

    @Test
    void itShouldSaveIngredientFromIngredientRequest() {
        //given
        IngredientRequest ingredientRequest = new IngredientRequest("Egg", 100.0, Category.BASE, true);
        Ingredient ingredient = new Ingredient(ingredientRequest.name(), ingredientRequest.price(), ingredientRequest.category(), ingredientRequest.healthy());

        //when
        ingredientService.save(ingredientRequest);

        //then
        verify(ingredientRepository).save(eq(ingredient));
    }

    @Test
    void itShouldUpdateIngredientIfIngredientIdIsCorrect() {
        //given
        Long ingredientId = 1L;
        IngredientRequest ingredientRequest = new IngredientRequest("Egg", 100.0, Category.BASE, true);
        Ingredient ingredientFromDatabase = new Ingredient(1L, "Egg", 10.0, Category.BASE, true);
        Ingredient updatedIngredient = new Ingredient(1L, "Egg", 100.0, Category.BASE, true);
        when(ingredientRepository.findById(eq(ingredientId))).thenReturn(Optional.of(ingredientFromDatabase));
        IngredientDto ingredientDto = IngredientDto.from(updatedIngredient);

        //when
        var answer = ingredientService.updateIngredient(ingredientId, ingredientRequest);

        //then
        verify(ingredientRepository).save(eq(updatedIngredient));
        assertEquals(ingredientDto, answer);
    }

    @Test
    void itShouldDeleteIngredientIfIngredientIdIsCorrect() {
        //given
        Long ingredientId = 1L;
        Ingredient ingredient = new Ingredient(1L, "Egg", 10.0, Category.BASE, true);
        when(ingredientRepository.findById(eq(ingredientId))).thenReturn(Optional.of(ingredient));

        //when
        ingredientService.deleteIngredient(ingredientId);

        //then
        verify(ingredientRepository).deleteById(eq(ingredientId));
    }
}