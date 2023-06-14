package com.mlutzdev.inventario.repository;

import com.mlutzdev.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface I_InventarioRepository extends JpaRepository<Inventario, Long> {

    List<Inventario> findByCodigoSkuIn(List<String> codigoSku);
}
