package com.nastyastrel.pancakes.service.order;

import com.nastyastrel.pancakes.model.order.Discount;
import com.nastyastrel.pancakes.model.order.DiscountValue;
import com.nastyastrel.pancakes.model.pancake.Pancake;
import com.nastyastrel.pancakes.model.pancake.PancakeDto;

import java.util.List;

public interface DiscountService {
    double calculateTotalPriceWithoutDiscount(List<PancakeDto> pancakeDtos);

    double calculatePancakePriceWithoutDiscount(Pancake pancake);

    double calculatePancakePriceWithDiscount(Pancake pancake, Discount discount);

    DiscountValue calculateDiscount(List<PancakeDto> pancakeDtos);
}
