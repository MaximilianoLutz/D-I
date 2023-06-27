package com.mlutzdev.inventario.rabbitmq;

import com.mlutzdev.inventario.utils.OrderDeserializer;
import com.mlutzdev.order.orderservice.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = { "${mlutzdev.queue.name}" })
    public void recibeMessage(@Payload String message){

        try {
            Order order = (Order) OrderDeserializer.deserialize(message);
            order.getOrderLineItems().forEach(System.out::println);
            log.info(order.toString());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        //log.info("Mensaje recibido: " + message);
    }
}
