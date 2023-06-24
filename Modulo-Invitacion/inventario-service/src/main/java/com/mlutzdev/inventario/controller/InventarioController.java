package com.mlutzdev.inventario.controller;

import com.mlutzdev.inventario.dto.InventarioDtoRequest;
import com.mlutzdev.inventario.dto.InventarioDtoResponse;
import com.mlutzdev.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping()
    public List<InventarioDtoResponse> isInStock(@RequestBody List<InventarioDtoRequest> codigoSku){

        return inventarioService.inStock(codigoSku);
    }
}
