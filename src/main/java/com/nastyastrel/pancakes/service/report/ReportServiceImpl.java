package com.nastyastrel.pancakes.service.report;

import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final IngredientRepository ingredientRepository;

    @Override
    public List<IngredientDto> findTheMostPopularOrderedIngredients(int month) {
        if (month == 0) {
            return ingredientRepository.findTheMostPopularIngredient(LocalDate.now().minusMonths(1).getMonthValue()).stream().map(IngredientDto::from).toList();
        } else {
            return ingredientRepository.findTheMostPopularIngredient(month).stream().map(IngredientDto::from).toList();
        }
    }

    @Override
    public List<IngredientDto> findTheMostPopularOrderedHealthyIngredients(int month) {
        if (month == 0) {
            return ingredientRepository.findTheMostPopularHealthyIngredient(LocalDate.now().minusMonths(1).getMonthValue()).stream().map(IngredientDto::from).toList();
        } else {
            return ingredientRepository.findTheMostPopularHealthyIngredient(month).stream().map(IngredientDto::from).toList();
        }
    }
}
