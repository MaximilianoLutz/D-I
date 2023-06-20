package com.mlutzdev.consumer;

import com.mlutzdev.consumer.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class ConsumerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent){
		log.info("Notificacion recibida desde orden ", orderPlacedEvent.getNumeroDePedido());

	}
}
