package com.mlutzdev.inventario.service;

import com.mlutzdev.inventario.dto.InventarioDtoRequest;
import com.mlutzdev.inventario.dto.InventarioDtoResponse;
import com.mlutzdev.inventario.repository.I_InventarioRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class InventarioService {

    @Autowired
    private I_InventarioRepository inventarioRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventarioDtoResponse> inStock(List<InventarioDtoRequest> inventarioDtoRequests) {

        List<String> codigoSku = inventarioDtoRequests
                .stream()
                .map(item -> item.getCodigoSku())
                .collect(Collectors.toList());

        return inventarioRepository.findByCodigoSkuIn(codigoSku)
                .stream()
                .map(inventario ->
                        InventarioDtoResponse.builder()
                                .codigoSku(inventario.getCodigoSku())
                                .inStock(inventario.getCantidad() >=
                                        (getCantidadItemDtoRequest(inventarioDtoRequests, inventario.getCodigoSku()))).build()
                ).collect(Collectors.toList());
    }
        private int getCantidadItemDtoRequest (List<InventarioDtoRequest> inventarioDtoRequests, String codigoSku){

            Optional<InventarioDtoRequest> lineItem = inventarioDtoRequests
                    .stream()
                    .filter(item -> codigoSku.equals(item.getCodigoSku()))
                    .findFirst();
            if(lineItem.isPresent()){
                return lineItem.get().getCantidad();
            }
            return 0 ;

    }

}
