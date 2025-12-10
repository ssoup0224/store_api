package com.store_api.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("Order not found.");
    }
}
