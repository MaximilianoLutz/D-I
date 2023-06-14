package com.mlutzdev.order.orderservice.repository;

import com.mlutzdev.order.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I_OrderRepository extends JpaRepository<Order, Long> {
}
