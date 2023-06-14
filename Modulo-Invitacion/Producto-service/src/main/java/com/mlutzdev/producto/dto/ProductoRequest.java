package com.mlutzdev.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
}
