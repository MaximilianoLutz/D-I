package com.mlutzdev.order.orderservice.service;

import com.mlutzdev.order.orderservice.dto.InventarioDtoRequest;
import com.mlutzdev.order.orderservice.dto.InventarioDtoResponse;
import com.mlutzdev.order.orderservice.dto.OrderLineItemsDto;
import com.mlutzdev.order.orderservice.dto.OrderDtoRequest;
import com.mlutzdev.order.orderservice.model.Order;
import com.mlutzdev.order.orderservice.model.OrderLineItems;
import com.mlutzdev.order.orderservice.publisher.Publisher;
import com.mlutzdev.order.orderservice.repository.I_OrderRepository;
import com.mlutzdev.order.orderservice.utils.OrderSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private I_OrderRepository i_OrderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Publisher publisher;

    @Autowired
    private OrderSerializer orderSerializer;


    @Transactional()
    public String placeOrder(OrderDtoRequest orderDtoRequest) throws IOException {
        Order order = new Order();
        order.setNumeroDePedido(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderDtoRequest
                .getOrderLineItemsDtosList()
                .stream().map(this::dtoToOrder).collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        List<InventarioDtoRequest> codigosSku = order.getOrderLineItems()
                .stream()
                .map(line -> new InventarioDtoRequest(line.getCodigoSku(), line.getCantidad())).collect(Collectors.toList());


        InventarioDtoResponse[] inventarioDtoResponsesArray = webClientBuilder.build().post()
                        .uri("http://inventario-service/api/inventario")
                        .body(Mono.just(codigosSku), new ParameterizedTypeReference<List<InventarioDtoRequest>>(){})
                        .retrieve()
                        .bodyToMono(InventarioDtoResponse[].class)
                        .block();



        boolean allProductsInStock = Arrays.stream(inventarioDtoResponsesArray)
                        .allMatch(InventarioDtoResponse::isInStock);

        if(allProductsInStock){

            publisher.send(OrderSerializer.getBiteArray(order));
            i_OrderRepository.save(order);
            return "Pedido realizado con éxito";

        }else{
            throw new IllegalArgumentException("Hay productos sin stock en el pedido solicitado");
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
