package com.mlutzdev.order.orderservice.controller;

import com.mlutzdev.order.orderservice.dto.OrderDtoRequest;
import com.mlutzdev.order.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name= "inventario", fallbackMethod = "fallBackMethodRealizarPedido")
    @TimeLimiter(name = "inventario")
    @Retry(name = "inventario")
    public CompletableFuture<String> realizarPedido(@RequestBody OrderDtoRequest orderDtoRequest){
        return CompletableFuture.supplyAsync(() -> {
            try {
                return orderService.placeOrder(orderDtoRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private CompletableFuture<String> fallBackMethodRealizarPedido(OrderDtoRequest orderDtoRequest, RuntimeException e){
        return CompletableFuture.supplyAsync(() -> e.getMessage());
    }
}
