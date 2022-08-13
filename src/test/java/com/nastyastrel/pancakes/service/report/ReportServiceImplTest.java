package com.nastyastrel.pancakes.service.report;

import com.nastyastrel.pancakes.model.pancake.Category;
import com.nastyastrel.pancakes.model.pancake.Ingredient;
import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    private IngredientRepository ingredientRepository;

    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl(ingredientRepository);
    }

    @Test
    void itShouldFindTheMostPopularOrderedIngredients() {
        //given
        int month = 9;
        List<Ingredient> expectedIngredients = List.of(new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false));
        List<IngredientDto> expectedIngredientsDto = expectedIngredients.stream().map(IngredientDto::from).toList();
        when(ingredientRepository.findTheMostPopularIngredient(eq(month))).thenReturn(expectedIngredients);

        //when
        var answer = reportService.findTheMostPopularOrderedIngredients(month);

        //then
        verify(ingredientRepository).findTheMostPopularIngredient(eq(month));
        assertEquals(expectedIngredientsDto, answer);
    }

    @Test
    void itShouldFindTheMostPopularOrderedHealthyIngredients() {
        //given
        int month = 9;
        List<Ingredient> expectedIngredients = List.of(new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false));
        List<IngredientDto> expectedIngredientsDto = expectedIngredients.stream().map(IngredientDto::from).toList();
        when(ingredientRepository.findTheMostPopularHealthyIngredient(eq(month))).thenReturn(expectedIngredients);

        //when
        var answer = reportService.findTheMostPopularOrderedHealthyIngredients(month);

        //then
        verify(ingredientRepository).findTheMostPopularHealthyIngredient(eq(month));
        assertEquals(expectedIngredientsDto, answer);
    }
}