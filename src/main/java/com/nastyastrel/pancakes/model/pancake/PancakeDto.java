package com.nastyastrel.pancakes.model.pancake;


import com.nastyastrel.pancakes.model.order.Discount;

import java.util.Set;
import java.util.stream.Collectors;

public record PancakeDto(Long pancakeId, Set<IngredientDto> ingredients, double priceWithoutDiscount, Discount discount, double priceWithDiscount) {
    public static PancakeDto from(Pancake pancake) {
        Set<IngredientDto> pancakeDtos = pancake.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet());
        return new PancakeDto(pancake.getPancakeId(), pancakeDtos, 0, Discount.NO_DISCOUNT, 0);
    }
}
