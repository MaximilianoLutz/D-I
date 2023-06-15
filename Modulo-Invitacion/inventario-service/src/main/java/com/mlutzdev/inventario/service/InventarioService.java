package com.mlutzdev.inventario.service;

import com.mlutzdev.inventario.controller.InventarioController;
import com.mlutzdev.inventario.dto.InventarioDtoResponse;
import com.mlutzdev.inventario.repository.I_InventarioRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class InventarioService {

    @Autowired
    private I_InventarioRepository inventarioRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventarioDtoResponse> inStock(List<String> codigoSku){
        log.info("wait start");
        Thread.sleep(5000);
        log.info("wait end");

        return inventarioRepository.findByCodigoSkuIn(codigoSku)
                .stream()
                .map(inventario ->
                    InventarioDtoResponse.builder()
                            .codigoSku(inventario.getCodigoSku())
                            .inStock(inventario.getCantidad() > 0).build()
                ).collect(Collectors.toList());
    }
}
