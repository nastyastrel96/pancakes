package com.nastyastrel.pancakes.service.order;

import com.nastyastrel.pancakes.exception.*;
import com.nastyastrel.pancakes.model.order.Discount;
import com.nastyastrel.pancakes.model.order.DiscountValue;
import com.nastyastrel.pancakes.model.order.Order;
import com.nastyastrel.pancakes.model.order.OrderDto;
import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.model.pancake.Pancake;
import com.nastyastrel.pancakes.model.pancake.PancakeDto;
import com.nastyastrel.pancakes.model.request.OrderCreateRequest;
import com.nastyastrel.pancakes.repository.OrderRepository;
import com.nastyastrel.pancakes.repository.PancakeRepository;
import com.nastyastrel.pancakes.service.pancake.PancakeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PancakeService pancakeService;
    private final PancakeRepository pancakeRepository;
    private final DiscountService discountService;

    @Override
    public OrderDto findById(Long orderId, Long userId) {
        Optional<Order> optionalOrder = orderRepository.findByOrderIdAndUserId(orderId, userId);
        Order order = optionalOrder.orElseThrow(() -> new NoSuchIdOrderException(orderId.toString()));
        return handlePriceAndDiscountForOrder(order);
    }

    @Override
    public OrderDto createOrder(OrderCreateRequest orderCreateRequest, Long userId) {
        if (!orderCreateRequest.pancakesId().isEmpty()) {
            if (pancakeService.checkIfPancakeIsVacant(orderCreateRequest.pancakesId())) {
                if (orderCreateRequest.pancakesId().stream().allMatch(pancakeService::hasValidIngredients)) {
                    Order order = new Order();
                    order.setUserId(userId);
                    order.setDescription(orderCreateRequest.description());
                    order.setCreationDate(LocalDateTime.now());
                    orderRepository.save(order);
                    List<Pancake> pancakesForNewOrder = orderCreateRequest.pancakesId().stream().map(pancakeId -> pancakeRepository.findById(pancakeId).orElseThrow(() -> new NoSuchIdPancakeException(pancakeId.toString()))).toList();
                    pancakeService.addPancakesToOrder(pancakesForNewOrder, order);

                    Order orderToReturn = orderRepository.findById(order.getOrderId()).orElseThrow(() -> new NoSuchIdOrderException(order.getOrderId().toString()));
                    orderToReturn.setPancakes(pancakesForNewOrder);
                    return handlePriceAndDiscountForOrder(orderToReturn);
                } else {
                    throw new NotValidPancakeForOrderException(orderCreateRequest.pancakesId().stream().filter(pancakeId -> !pancakeService.hasValidIngredients(pancakeId)).findFirst().get().toString());
                }
            } else throw new NotVacantPancakeException();
        } else {
            throw new NotValidOrderForCreationException();
        }
    }

    private OrderDto handlePriceAndDiscountForOrder(Order order) {
        List<PancakeDto> pancakeDtos = order.getPancakes()
                .stream()
                .map(pancake -> new PancakeDto(pancake.getPancakeId(), pancake.getIngredients()
                        .stream()
                        .map(IngredientDto::from)
                        .collect(Collectors.toSet()), discountService.calculatePancakePriceWithoutDiscount(pancake), Discount.NO_DISCOUNT, 0))
                .toList();

        double totalPrice = discountService.calculateTotalPriceWithoutDiscount(pancakeDtos);
        DiscountValue discountValue = discountService.calculateDiscount(pancakeDtos);
        OrderDto orderDto = new OrderDto(order.getOrderId(), pancakeDtos, order.getDescription(), order.getCreationDate(), order.getUserId(), totalPrice, discountValue.discount(), discountValue.price());
        List<PancakeDto> pancakeDtosToReturn = order.getPancakes()
                .stream()
                .map(pancake -> new PancakeDto(pancake.getPancakeId(), pancake.getIngredients()
                        .stream()
                        .map(IngredientDto::from)
                        .collect(Collectors.toSet()), discountService.calculatePancakePriceWithoutDiscount(pancake), orderDto.discount(), discountService.calculatePancakePriceWithDiscount(pancake, orderDto.discount())))
                .toList();
        return new OrderDto(order.getOrderId(), pancakeDtosToReturn, order.getDescription(), order.getCreationDate(), order.getUserId(), totalPrice, discountValue.discount(), discountValue.price());
    }
}
