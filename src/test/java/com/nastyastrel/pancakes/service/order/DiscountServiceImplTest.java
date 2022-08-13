package com.nastyastrel.pancakes.service.order;

import com.nastyastrel.pancakes.model.order.Discount;
import com.nastyastrel.pancakes.model.order.DiscountValue;
import com.nastyastrel.pancakes.model.pancake.*;
import com.nastyastrel.pancakes.service.pancake.PancakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {
    @Mock
    private PancakeService pancakeService;

    private DiscountServiceImpl discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl(pancakeService);
    }

    @Test
    void itShouldCalculateTotalPriceWithoutDiscount() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 80.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        PancakeDto pancakeDtoToCalculatePrice1 = new PancakeDto(pancake1.getPancakeId(), pancake1.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 190.0, Discount.NO_DISCOUNT, 0.0);
        PancakeDto pancakeDtoToCalculatePrice2 = new PancakeDto(pancake2.getPancakeId(), pancake2.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 110.0, Discount.NO_DISCOUNT, 0.0);
        List<PancakeDto> pancakeDtoListToCalculatePrice = List.of(pancakeDtoToCalculatePrice1, pancakeDtoToCalculatePrice2);

        //when
        var answer = discountService.calculateTotalPriceWithoutDiscount(pancakeDtoListToCalculatePrice);

        //then
        assertEquals(300.0, answer);
    }

    @Test
    void itShouldCalculatePancakePriceWithoutDiscount() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 80.0, Category.FRUIT, true)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);

        //when
        var answer = discountService.calculatePancakePriceWithoutDiscount(pancake1);

        //then
        assertEquals(190.0, answer);
    }

    @Test
    void itShouldCalculatePancakePriceWithNoDiscount() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 80.0, Category.FRUIT, true)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);

        //when
        var answer = discountService.calculatePancakePriceWithDiscount(pancake1, Discount.NO_DISCOUNT);

        //then
        assertEquals(190.0, answer);
    }

    @Test
    void itShouldCalculatePancakePriceWithDiscountFive() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 80.0, Category.FRUIT, true)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);

        //when
        var answer = discountService.calculatePancakePriceWithDiscount(pancake1, Discount.FIVE_PERCENT);

        //then
        assertEquals(180.5, answer);
    }

    @Test
    void itShouldCalculatePancakePriceWithDiscountTen() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 80.0, Category.FRUIT, true)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);

        //when
        var answer = discountService.calculatePancakePriceWithDiscount(pancake1, Discount.TEN_PERCENT);

        //then
        assertEquals(171.0, answer);
    }

    @Test
    void itShouldCalculatePancakePriceWithDiscountFifteen() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 80.0, Category.FRUIT, true)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);

        //when
        var answer = discountService.calculatePancakePriceWithDiscount(pancake1, Discount.FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE);

        //then
        assertEquals(161.5, answer);
    }

    @Test
    void itShouldCalculateNoDiscount() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 40.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 30.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 10.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        PancakeDto pancakeDtoToCalculateDiscount1 = new PancakeDto(pancake1.getPancakeId(), pancake1.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 80.0, Discount.NO_DISCOUNT, 0.0);
        PancakeDto pancakeDtoToCalculateDiscount2 = new PancakeDto(pancake2.getPancakeId(), pancake2.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 20.0, Discount.NO_DISCOUNT, 0.0);
        List<PancakeDto> pancakeDtoListToCalculateDiscount = List.of(pancakeDtoToCalculateDiscount1, pancakeDtoToCalculateDiscount2);

        //when
        var answer = discountService.calculateDiscount(pancakeDtoListToCalculateDiscount);

        //then
        assertEquals(new DiscountValue(Discount.NO_DISCOUNT, 100.0), answer);
    }

    @Test
    void itShouldCalculateDiscountFive() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 40.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 40.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 10.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 10.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        PancakeDto pancakeDtoToCalculateDiscount1 = new PancakeDto(pancake1.getPancakeId(), pancake1.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 90.0, Discount.NO_DISCOUNT, 0.0);
        PancakeDto pancakeDtoToCalculateDiscount2 = new PancakeDto(pancake2.getPancakeId(), pancake2.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 20.0, Discount.NO_DISCOUNT, 0.0);
        List<PancakeDto> pancakeDtoListToCalculateDiscount = List.of(pancakeDtoToCalculateDiscount1, pancakeDtoToCalculateDiscount2);

        //when
        var answer = discountService.calculateDiscount(pancakeDtoListToCalculateDiscount);

        //then
        assertEquals(new DiscountValue(Discount.FIVE_PERCENT, 104.5), answer);
    }

    @Test
    void itShouldCalculateDiscountTen() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 40.0, Category.TOPPING, false),
                new Ingredient(3L, "Raspberry", 40.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 100.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        PancakeDto pancakeDtoToCalculateDiscount1 = new PancakeDto(pancake1.getPancakeId(), pancake1.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 100.0, Discount.NO_DISCOUNT, 0.0);
        PancakeDto pancakeDtoToCalculateDiscount2 = new PancakeDto(pancake2.getPancakeId(), pancake2.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 120.0, Discount.NO_DISCOUNT, 0.0);
        List<PancakeDto> pancakeDtoListToCalculateDiscount = List.of(pancakeDtoToCalculateDiscount1, pancakeDtoToCalculateDiscount2);

        //when
        var answer = discountService.calculateDiscount(pancakeDtoListToCalculateDiscount);

        //then
        assertEquals(new DiscountValue(Discount.TEN_PERCENT, 198.0), answer);
    }

    @Test
    void itShouldCalculateDiscountFifteen() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        PancakeDto pancakeDtoToCalculateDiscount1 = new PancakeDto(pancake1.getPancakeId(), pancake1.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 220.0, Discount.NO_DISCOUNT, 0.0);
        PancakeDto pancakeDtoToCalculateDiscount2 = new PancakeDto(pancake2.getPancakeId(), pancake2.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 70.0, Discount.NO_DISCOUNT, 0.0);
        List<PancakeDto> pancakeDtoListToCalculateDiscount = List.of(pancakeDtoToCalculateDiscount1, pancakeDtoToCalculateDiscount2);
        when(pancakeService.isHealthyPancake(pancakeDtoToCalculateDiscount1)).thenReturn(true);

        //when
        var answer = discountService.calculateDiscount(pancakeDtoListToCalculateDiscount);

        //then
        assertEquals(new DiscountValue(Discount.FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE, 257.0), answer);
    }
}