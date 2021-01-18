package com.experis.worldoffice.shoppingcart.service;

import com.experis.worldoffice.shoppingcart.exception.FailedRequestProductException;

public interface ProductService {

    Long getCurrentExistence(Long productId) throws FailedRequestProductException;
    Boolean decreaseProductStock(Long productId) throws InstantiationException;
    void increaseProductStock(Long productId) throws InstantiationException;
}
