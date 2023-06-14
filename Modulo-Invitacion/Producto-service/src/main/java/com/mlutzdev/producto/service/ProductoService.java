package com.mlutzdev.producto.service;

import com.mlutzdev.producto.dto.ProductoRequest;
import com.mlutzdev.producto.dto.ProductoResponse;
import com.mlutzdev.producto.model.Producto;
import com.mlutzdev.producto.repoisitory.I_ProductoRepositoru;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private I_ProductoRepositoru productoRepositoru;

    public void guardarProducto(ProductoRequest productoRequest){
        Producto producto = Producto.builder()
                .nombre(productoRequest.getNombre())
                .descripcion(productoRequest.getDescripcion())
                .precio(productoRequest.getPrecio())
                .build();
        productoRepositoru.save(producto);
        log.info("El producto {" +producto.getId()+"} ha sido guardado con éxito" );
    }

    public List<ProductoResponse> getAllProductos(){
         List<Producto>  productos= productoRepositoru.findAll();

         return productos.stream().map(this::mapProductoToResponse).collect(Collectors.toList());
    }

    private ProductoResponse mapProductoToResponse(Producto producto){
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .build();
    }
}
