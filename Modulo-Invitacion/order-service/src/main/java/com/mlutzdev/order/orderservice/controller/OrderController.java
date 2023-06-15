package com.mlutzdev.order.orderservice.controller;

import com.mlutzdev.order.orderservice.dto.OrderRequest;
import com.mlutzdev.order.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name= "inventario", fallbackMethod = "fallBackMethodRealizarPedido")
    @TimeLimiter(name = "inventario")
    @Retry(name = "inventario")
    public CompletableFuture<String> realizarPedido(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));

    }

    private CompletableFuture<String> fallBackMethodRealizarPedido(OrderRequest orderRequest, RuntimeException e){
        return CompletableFuture.supplyAsync(() -> "Opss ha ocurrido un error al realizar el pedido");
    }
}
