package com.mlutzdev.producto.controller;

import com.mlutzdev.producto.dto.ProductoRequest;
import com.mlutzdev.producto.dto.ProductoResponse;
import com.mlutzdev.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearProducto(@RequestBody ProductoRequest productoRequest){
        productoService.guardarProducto(productoRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoResponse>  listarProductos(){
        return productoService.getAllProductos();
    }
}
