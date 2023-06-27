package com.mlutzdev.order.orderservice.handlers;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> notFound(IllegalArgumentException e) {

        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseEntity<String> InputOutput(IOException e) {

        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST );
    }

}
