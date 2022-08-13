package com.nastyastrel.pancakes.service.order;

import com.nastyastrel.pancakes.model.order.Discount;
import com.nastyastrel.pancakes.model.order.DiscountValue;
import com.nastyastrel.pancakes.model.pancake.Ingredient;
import com.nastyastrel.pancakes.model.pancake.Pancake;
import com.nastyastrel.pancakes.model.pancake.PancakeDto;
import com.nastyastrel.pancakes.service.pancake.PancakeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final PancakeService pancakeService;

    @Override
    public double calculateTotalPriceWithoutDiscount(List<PancakeDto> pancakeDtos) {
        return pancakeDtos.stream().mapToDouble(PancakeDto::priceWithoutDiscount).sum();
    }

    @Override
    public double calculatePancakePriceWithoutDiscount(Pancake pancake) {
        return pancake.getIngredients().stream().mapToDouble(Ingredient::getPrice).sum();
    }

    @Override
    public double calculatePancakePriceWithDiscount(Pancake pancake, Discount discount) {
        double priceWithoutDiscount = calculatePancakePriceWithoutDiscount(pancake);
        return switch (discount) {
            case FIVE_PERCENT -> priceWithoutDiscount * Discount.FIVE_PERCENT.getValue();
            case TEN_PERCENT -> priceWithoutDiscount * Discount.TEN_PERCENT.getValue();
            case FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE ->
                    priceWithoutDiscount * Discount.FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE.getValue();
            case NO_DISCOUNT -> priceWithoutDiscount * Discount.NO_DISCOUNT.getValue();
        };
    }

    @Override
    public DiscountValue calculateDiscount(List<PancakeDto> pancakeDtos) {
        double totalPrice = calculateTotalPriceWithoutDiscount(pancakeDtos);
        Map<Discount, Double> map = new HashMap<>();
        if (totalPrice > 100 && totalPrice <= 200) {
            map.put(Discount.FIVE_PERCENT, calculatePriceWithDiscountFive(totalPrice));
        }
        if (totalPrice > 200) {
            map.put(Discount.TEN_PERCENT, calculatePriceWithDiscountTen(totalPrice));
        }
        if (ifHealthyDiscount(pancakeDtos)) {
            map.put(Discount.FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE, calculatePriceWithDiscountFifteenHealthy(pancakeDtos));
        }
        if (!map.isEmpty()) {
            return new DiscountValue(map.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey(), map.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue());
        } else {
            return new DiscountValue(Discount.NO_DISCOUNT, totalPrice);
        }
    }

    private boolean ifHealthyDiscount(List<PancakeDto> pancakeDtos) {
        return pancakeDtos.stream().anyMatch(pancakeService::isHealthyPancake);
    }

    private double calculatePriceWithDiscountFive(double totalPrice) {
        return totalPrice * Discount.FIVE_PERCENT.getValue();
    }

    private double calculatePriceWithDiscountTen(double totalPrice) {
        return totalPrice * Discount.TEN_PERCENT.getValue();
    }

    private double calculatePriceWithDiscountFifteenHealthy(List<PancakeDto> pancakeDtos) {
        List<PancakeDto> updatedPancakeDtos = pancakeDtos.stream().map(pancakeDto -> {
            if (pancakeService.isHealthyPancake(pancakeDto)) {
                return new PancakeDto(pancakeDto.pancakeId(), pancakeDto.ingredients(), pancakeDto.priceWithoutDiscount(), Discount.FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE, pancakeDto.priceWithoutDiscount() * Discount.FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE.getValue());
            } else return pancakeDto;
        }).toList();
        double priceWithoutDiscount = updatedPancakeDtos.stream().filter(pancakeDto -> pancakeDto.priceWithDiscount() == 0).mapToDouble(PancakeDto::priceWithoutDiscount).sum();
        double priceWithDiscount = updatedPancakeDtos.stream().filter(pancakeDto -> pancakeDto.priceWithDiscount() != 0).mapToDouble(PancakeDto::priceWithDiscount).sum();
        return priceWithoutDiscount + priceWithDiscount;
    }
}
