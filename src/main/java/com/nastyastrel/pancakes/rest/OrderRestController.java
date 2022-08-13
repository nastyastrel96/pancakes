package com.nastyastrel.pancakes.rest;

import com.nastyastrel.pancakes.model.order.OrderDto;
import com.nastyastrel.pancakes.model.request.OrderCreateRequest;
import com.nastyastrel.pancakes.model.user.User;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import com.nastyastrel.pancakes.service.order.OrderService;
import com.nastyastrel.pancakes.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/orders/{orderId}")
    public OrderDto findById(@PathVariable Long orderId, Principal principal) {
        User user = userService.getAuthenticatedUser(principal);
        return orderService.findById(orderId, user.getUserId());
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderCreateRequest orderCreateRequest, Principal principal) {
        User user = userService.getAuthenticatedUser(principal);
        return orderService.createOrder(orderCreateRequest, user.getUserId());
    }
}
