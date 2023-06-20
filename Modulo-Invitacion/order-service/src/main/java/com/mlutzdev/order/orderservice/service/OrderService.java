package com.mlutzdev.order.orderservice.service;


import com.mlutzdev.order.orderservice.dto.InventarioResponse;
import com.mlutzdev.order.orderservice.dto.OrderLineItemsDto;
import com.mlutzdev.order.orderservice.dto.OrderRequest;
import com.mlutzdev.order.orderservice.event.OrderPlacedEvent;
import com.mlutzdev.order.orderservice.model.Order;
import com.mlutzdev.order.orderservice.model.OrderLineItems;
import com.mlutzdev.order.orderservice.repository.I_OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Autowired
    private I_OrderRepository i_OrderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Tracer tracer;

    @Transactional
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setNumeroDePedido(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest
                .getOrderLineItemsDtosList()
                .stream().map(this::dtoToOrder).collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        List<String> codigosSku = order.getOrderLineItems()
                .stream()
                .map(OrderLineItems::getCodigoSku).collect(Collectors.toList());

        Span inventarioServiceLookup  = tracer.nextSpan().name("InventarioServiceSpan");

        try(Tracer.SpanInScope isLookup = tracer.withSpan(inventarioServiceLookup.start())){
            inventarioServiceLookup.tag("call","inventario-service");


            InventarioResponse [] inventarioResponsesArray = webClientBuilder.build().get()
                        .uri("http://inventario-service/api/inventario", uriBuilder -> uriBuilder.queryParam("codigoSku",codigosSku).build())
                        .retrieve()
                        .bodyToMono(InventarioResponse[].class)
                        .block();
            boolean allProductsInStock = Arrays.stream(inventarioResponsesArray)
                    .allMatch(InventarioResponse::isInStock);

            if(allProductsInStock){
                i_OrderRepository.save(order);
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getNumeroDePedido()));
                return "Pedido realizado con éxito";
            }else{
                throw new IllegalArgumentException("Hay productos sin stock en el pedido solicitado");
            }
        }finally {
            inventarioServiceLookup.end();
        }

    }
    private OrderLineItems dtoToOrder(OrderLineItemsDto orderLineItemsDto){
        return OrderLineItems.builder()
                .codigoSku(orderLineItemsDto.getCodigoSku())
                .precio(orderLineItemsDto.getPrecio())
                .cantidad(orderLineItemsDto.getCantidad())
                .build();
    }

}
