package com.nastyastrel.pancakes.service.report;

import com.nastyastrel.pancakes.model.pancake.IngredientDto;

import java.util.List;

public interface ReportService {
    List<IngredientDto> findTheMostPopularOrderedIngredients(int month);

    List<IngredientDto> findTheMostPopularOrderedHealthyIngredients(int month);
}
