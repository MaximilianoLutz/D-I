package com.mlutzdev.producto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(value = "producto")
public class Producto {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
}
