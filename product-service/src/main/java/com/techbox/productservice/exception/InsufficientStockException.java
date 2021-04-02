package com.techbox.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Product has less units in inventory")
public class InsufficientStockException extends Exception {

    public InsufficientStockException( ) {
        super();
    }
}
