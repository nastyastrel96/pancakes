package com.nastyastrel.pancakes.service.order;

import com.nastyastrel.pancakes.model.order.OrderDto;
import com.nastyastrel.pancakes.model.request.OrderCreateRequest;


public interface OrderService {
    OrderDto findById(Long orderId, Long userId);

    OrderDto createOrder(OrderCreateRequest orderCreaterequest, Long userId);
}
