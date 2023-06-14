package com.mlutzdev.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventarioDtoResponse {

    private String codigoSku;
    private boolean inStock;
}
