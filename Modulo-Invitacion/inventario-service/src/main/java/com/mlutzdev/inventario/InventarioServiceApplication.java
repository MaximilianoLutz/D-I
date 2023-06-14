package com.mlutzdev.inventario;

import com.mlutzdev.inventario.model.Inventario;
import com.mlutzdev.inventario.repository.I_InventarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(I_InventarioRepository inventarioRepository){
//		return args -> {
//			Inventario inventario1 = new Inventario();
//			Inventario inventario2 = new Inventario();
//
//			inventario1.setCantidad(100);
//			inventario1.setCodigoSku("maquina_humo");
//			inventario2.setCantidad(10);
//			inventario2.setCodigoSku("musical_cumbia");
//
//			inventarioRepository.save(inventario1);
//			inventarioRepository.save(inventario2);
//		};
//	}

}
