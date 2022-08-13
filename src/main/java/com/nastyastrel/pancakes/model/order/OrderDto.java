package com.nastyastrel.pancakes.model.order;

import com.nastyastrel.pancakes.model.pancake.PancakeDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(Long orderId, List<PancakeDto> pancakes, String description, LocalDateTime creationDate, Long userId, double orderPriceWithoutDiscount, Discount discount, double orderPriceWithDiscount) {
    public static OrderDto from(Order order) {
        List<PancakeDto> pancakeDtos = order.getPancakes().stream().map(PancakeDto::from).toList();
        return new OrderDto(order.getOrderId(), pancakeDtos, order.getDescription(), order.getCreationDate(), order.getUserId(), 0, Discount.NO_DISCOUNT, 0);
    }
}
