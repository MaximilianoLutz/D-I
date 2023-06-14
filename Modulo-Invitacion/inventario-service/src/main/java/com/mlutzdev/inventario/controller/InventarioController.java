package com.mlutzdev.inventario.controller;

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

    @GetMapping()
    public List<InventarioDtoResponse> isInStock(@RequestParam List<String> codigoSku){
        return inventarioService.inStock(codigoSku);
    }
}
