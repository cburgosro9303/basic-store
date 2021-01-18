package com.experis.worldoffice.shoppingcart.service;

import com.experis.worldoffice.shoppingcart.dto.CartProductDto;
import com.experis.worldoffice.shoppingcart.dto.ProductDto;
import com.experis.worldoffice.shoppingcart.dto.ShoppingCartDto;
import com.experis.worldoffice.shoppingcart.exception.BuyClosedException;
import com.experis.worldoffice.shoppingcart.exception.FailedRequestProductException;
import com.experis.worldoffice.shoppingcart.exception.InsufficientStockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {

    ShoppingCartDto addProduct(ProductDto productDto) throws FailedRequestProductException, InsufficientStockException, BuyClosedException;
    ShoppingCartDto addProduct(ProductDto productDto,Long purchaseId) throws FailedRequestProductException, InsufficientStockException, BuyClosedException;

    Page<CartProductDto> getCurrentProducts(Long purchaseId, Pageable pageable);

    Boolean cleanShoppingCart(Long purchaseId);

    ShoppingCartDto finishBuying(Long purchaseId) throws InstantiationException, InsufficientStockException;

}
