package com.experis.worldoffice.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE,reason = "Product Service is failing")
public class FailedRequestProductException extends Exception {
    public FailedRequestProductException() {

    }
}
