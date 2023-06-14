package com.mlutzdev.order.orderservice.controller;

import com.mlutzdev.order.orderservice.dto.OrderRequest;
import com.mlutzdev.order.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String realizarPedido(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Pedido realizado con éxito";
    }
}
