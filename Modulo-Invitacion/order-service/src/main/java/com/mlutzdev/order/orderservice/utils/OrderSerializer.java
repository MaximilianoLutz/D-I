package com.mlutzdev.order.orderservice.utils;

import com.mlutzdev.order.orderservice.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

@Component
public class OrderSerializer {

    public static String getBiteArray(Order order) throws IOException {
        try(
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        ){
            objectOutputStream.writeObject(order);
            final byte[] byteArray = out.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        }
    }
}
