package com.mlutzdev.producto.repoisitory;

import com.mlutzdev.producto.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface I_ProductoRepositoru extends MongoRepository<Producto, String> {

}
