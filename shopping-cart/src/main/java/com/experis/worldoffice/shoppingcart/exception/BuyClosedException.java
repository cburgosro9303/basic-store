package com.experis.worldoffice.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED,reason = "Purchase is closed")
public class BuyClosedException extends Exception {
}
