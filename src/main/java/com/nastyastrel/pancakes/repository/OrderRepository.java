package com.nastyastrel.pancakes.repository;

import com.nastyastrel.pancakes.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderIdAndUserId(Long orderId, Long userId);
}
