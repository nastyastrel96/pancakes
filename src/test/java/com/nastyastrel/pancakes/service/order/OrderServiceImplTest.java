package com.nastyastrel.pancakes.service.order;

import com.nastyastrel.pancakes.exception.*;
import com.nastyastrel.pancakes.model.order.Discount;
import com.nastyastrel.pancakes.model.order.DiscountValue;
import com.nastyastrel.pancakes.model.order.Order;
import com.nastyastrel.pancakes.model.order.OrderDto;
import com.nastyastrel.pancakes.model.pancake.*;
import com.nastyastrel.pancakes.model.request.OrderCreateRequest;
import com.nastyastrel.pancakes.repository.OrderRepository;
import com.nastyastrel.pancakes.repository.PancakeRepository;
import com.nastyastrel.pancakes.service.pancake.PancakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PancakeService pancakeService;
    @Mock
    private PancakeRepository pancakeRepository;
    @Mock
    private DiscountService discountService;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository, pancakeService, pancakeRepository, discountService);
    }

    @Test
    void itShouldFindOrderByIdAndReturnOrderDto() {
        //given
        Long userId = 1L;
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
        List<PancakeDto> pancakeDtoToCalculatePrice = List.of(pancakeDtoToCalculatePrice1, pancakeDtoToCalculatePrice2);
        when(discountService.calculateDiscount(pancakeDtoToCalculatePrice)).thenReturn(new DiscountValue(Discount.TEN_PERCENT, 270.0));
        when(discountService.calculatePancakePriceWithoutDiscount(pancake1)).thenReturn(190.0);
        when(discountService.calculatePancakePriceWithoutDiscount(pancake2)).thenReturn(110.0);
        when(discountService.calculatePancakePriceWithDiscount(pancake1, Discount.TEN_PERCENT)).thenReturn(171.0);
        when(discountService.calculatePancakePriceWithDiscount(pancake2, Discount.TEN_PERCENT)).thenReturn(99.0);
        when(discountService.calculateTotalPriceWithoutDiscount(pancakeDtoToCalculatePrice)).thenReturn(300.0);

        PancakeDto pancakeDtoToReturn1 = new PancakeDto(pancake1.getPancakeId(), pancake1.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 190.0, Discount.TEN_PERCENT, 171.0);
        PancakeDto pancakeDtoToReturn2 = new PancakeDto(pancake2.getPancakeId(), pancake2.getIngredients().stream().map(IngredientDto::from).collect(Collectors.toSet()), 110.0, Discount.TEN_PERCENT, 99.0);
        List<PancakeDto> pancakeDtoListToReturn = List.of(pancakeDtoToReturn1, pancakeDtoToReturn2);
        Order order = new Order(1L, List.of(pancake1, pancake2), " ", LocalDateTime.of(2021, 9, 20, 21, 48, 22), userId);
        pancake1.setOrder(order);
        pancake2.setOrder(order);
        OrderDto orderDto = new OrderDto(order.getOrderId(), pancakeDtoListToReturn, order.getDescription(), order.getCreationDate(), order.getUserId(), 300.0, Discount.TEN_PERCENT, 270.0);
        when(orderRepository.findByOrderIdAndUserId(order.getOrderId(), userId)).thenReturn(Optional.of(order));

        //when
        var answer = orderService.findById(order.getOrderId(), userId);

        //then
        assertEquals(orderDto, answer);
    }

    @Test
    void itShouldThrowExceptionIfOrderDoesNotHavePancakes() {
        //given
        Long userId = 1L;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(Collections.emptyList());

        //then
        Throwable exception = assertThrows(NotValidOrderForCreationException.class, () -> orderService.createOrder(orderCreateRequest, userId));
        assertEquals("Order is not valid for creation. Valid order should have at least 1 pancake", exception.getMessage());
    }

    @Test
    void itShouldThrowExceptionIfOrderDoesNotHaveVacantPancakes() {
        //given
        Long userId = 1L;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of(1L, 2L));
        when(pancakeService.checkIfPancakeIsVacant(orderCreateRequest.pancakesId())).thenReturn(false);

        //then
        Throwable exception = assertThrows(NotVacantPancakeException.class, () -> orderService.createOrder(orderCreateRequest, userId));
        assertEquals("At first you need to create pancakes and then to add them to the order", exception.getMessage());
    }

    @Test
    void itShouldThrowExceptionIfNotEachPancakeIsValidForTheOrder() {
        //given
        Long userId = 1L;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of(1L, 2L));
        when(pancakeService.checkIfPancakeIsVacant(orderCreateRequest.pancakesId())).thenReturn(true);
        when(pancakeService.hasValidIngredients(1L)).thenReturn(false);

        //then
        Throwable exception = assertThrows(NotValidPancakeForOrderException.class, () -> orderService.createOrder(orderCreateRequest, userId));
        assertEquals("Pancake with id = 1 is not valid. Valid pancake should have only 1 ingredient as the base and at least 1 ingredient as a filling", exception.getMessage());
    }
}