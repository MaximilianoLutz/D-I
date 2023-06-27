package com.mlutzdev.inventario.utils;

import com.mlutzdev.order.orderservice.model.Order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class OrderDeserializer {

    public static Object deserialize(String byteString) throws IOException, ClassNotFoundException{

        final byte[] bytes = Base64.getDecoder().decode(byteString);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }
}
